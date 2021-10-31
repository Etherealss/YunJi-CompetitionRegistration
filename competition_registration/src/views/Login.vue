<template>
  <div>
    <Header />
    <!--直接显示的界面，是登录表单，但是包含了注册按钮，可以打开注册模态框-->
    <div class="signConstainter">
      <h2 class="formTitle">登录</h2>
      <el-form
        :model="loginForm"
        status-icon
        :rules="rules"
        ref="loginForm"
        label-width="100px"
      >
        <div id="loginBox">
          <el-form-item label="用户名" prop="username">
            <el-input
              type="text"
              v-model="loginForm.username"
              autocomplete="off"
              id="loginUsername"
              placeholder="请输入您的用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              type="password"
              v-model="loginForm.password"
              autocomplete="off"
              id="loginPassword"
              placeholder="请输入您的用户密码"
            ></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="captcha">
            <el-col id="loginCaptchaInput" :span="11">
              <el-input
                type="text"
                v-model="loginForm.captcha"
                autocomplete="off"
                placeholder="请输入验证码"
              ></el-input>
            </el-col>
            <el-col :span="11">
              <img
                src="/api/captcha"
                alt="验证码"
                id="loginCaptchaImg"
                ref="captchaImg"
                @click="changeCaptcha()"
              />
            </el-col>
          </el-form-item>
          <el-form-item id="loginBtnBox">
            <el-button type="primary" @click="submitLogin('loginForm')"
              >登录</el-button
            >
            <el-button @click="toRegister()">注册</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "@/components/header/Header";
import util from "@/utils/AppUtil.js";
export default {
  name: "Login",
  data() {
    var validateUsername = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else if (value.length > 20 || value.length < 6) {
        callback(new Error("用户名在6~20个字符之间"));
      } else {
        callback();
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length > 20 || value.length < 6) {
        callback(new Error("密码在6~20个字符之间"));
      } else {
        callback();
      }
    };
    var validateCaptcha = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入验证码"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        // 默认值
        username: "123123",
        password: "123123",
        captcha: "",
      },
      rules: {
        username: [{ validator: validateUsername, trigger: "input" }],
        password: [{ validator: validatePassword, trigger: "input" }],
        captcha: [{ validator: validateCaptcha, trigger: "input" }],
      },
    };
  },
  methods: {
    /**
     * 登录请求
     */
    submitLogin(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return false;
        }
        this.axiosToken();

        // 更新验证码
        this.changeCaptcha();
      });
    },
    axiosToken() {
      let data = this.qs.stringify({
        username: this.loginForm.username,
        password: this.loginForm.password,
        grant_type: "password",
        scope: "all",
      });
      // 请求后端
      this.$axios({
        method: "post",
        url: "/oauth/token",
        data,
        auth: {
          username: "client_id",
          password: "secret",
        },
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
        .then((response) => {
          console.log(response);
          this.$store.commit("setTokenDetails", response);
          this.axiosLogin();
        })
        .catch(() => {});
    },
    axiosLogin() {
      // 表单格式提交数据：
      // https://www.cnblogs.com/chcindy/p/6322809.html
      // https://www.jianshu.com/p/fd7bf46d4412
      // https://zhuanlan.zhihu.com/p/41948102
      let data = this.qs.stringify({
        username: this.loginForm.username,
        password: this.loginForm.password,
        // 后台通过 loginCaptcha 获取用户输入的验证码
        loginCaptcha: this.loginForm.captcha,
      });
      // 请求后端
      this.$axios({
        method: "post",
        url: "/users/login",
        data,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
        .then((response) => {
          if (response.code === 200) {
            this.$notify({
              title: "登录成功",
              message: "欢迎用户 " + response.data.username + " 登录！",
              type: "success",
              duration: 2000,
            });
            this.$store.commit("setUserDetails", response.data);
            this.$router.push({
              name: "index",
            });
          } else {
            this.renderResult(response);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    /**
     * 重置表单
     */
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /**
     * 更新验证码图片
     */
    changeCaptcha() {
      util.changeCaptcha(this.$refs.captchaImg);
    },
    /**
     * 登录请求后渲染页面，给出提示
     */
    renderResult(data) {
      let code = data.code;
      if (code >= 10300) {
        if (code == 10301) {
          this.notifySuccess("未输入验证码！");
        } else if (code == 10302) {
          this.notifySuccess("验证码不正确！");
        } else if (code == 10303) {
          this.notifySuccess("验证码已失效！请刷新");
        } else {
          this.notifySuccess("验证码异常！请稍后重试！");
        }
      }
      if (code >= 10200) {
        if (code == 10201) {
          this.notifySuccess("您输入的密码不正确！");
        } else if (code == 10202) {
          this.notifySuccess("您输入用户不存在！");
        } else if (code == 10202) {
          this.notifySuccess("该账号已登录，请勿重复登录！");
        }
      }
    },
    notifyException() {
      this.$notify({
        title: "登录失败",
        message: "服务器异常！",
        type: "warning",
        duration: 2000,
      });
    },
    notifySuccess(message) {
      this.$notify({
        title: "登录失败",
        message,
        type: "warning",
        duration: 2000,
      });
    },
    toRegister() {
      this.$router.push("/register");
    },
  },
  components: { Header },
};
</script>

<style>
.signConstainter {
  margin: 50px 0 0 0;
  align-items: center;
  text-align: center;
}

#signBox {
  background-color: #f7f7f7;
  margin: 0 207px;
  box-shadow: 0 2px 4px rgb(0 0 0 / 12%);
}

#loginBox {
  display: inline-block;
  width: 30%;
  margin: 0 81px 0 0;
}

.signInput {
  width: 90%;
  padding: 8px;
  margin: 8px 0;
  border: none;
  outline: none;
  font-size: 14px;
  letter-spacing: 1px;
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.25);
  background-color: #f5f5f5;
}

#loginBtnBox {
  /*与输入框隔开*/
  margin-top: 20px;
}

#registerBtnBox {
  margin: 6px 0 0 0;
}

.formTitle {
  font-size: 25px;
  font-weight: bold;
  letter-spacing: 20px;
  text-indent: 20px;
  text-align: center;
  margin: 0 0 30px 0;
}
/* #loginCaptchaInput {
  width: 50%;
} */
#loginCaptchaImg {
  cursor: pointer;
  margin: 20px 0 0 0;
  width: 66px;
  height: 33px;
}
#loginCaptcha {
  width: 40%;
  margin: 0 50px 0 0;
}
</style>