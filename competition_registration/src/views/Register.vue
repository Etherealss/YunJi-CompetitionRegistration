<template>
  <div>
    <Header />
    <div id="signUpBody">
      <h2 class="formTitle">注册</h2>
      <el-form
        :model="signUpForm"
        :rules="rules"
        ref="signUpForm"
        label-width="100px"
      >
        <div id="signUpFormItems">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="signUpForm.username"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="signUpForm.nickname"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input
              type="password"
              v-model="signUpForm.pass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input
              type="password"
              v-model="signUpForm.checkPass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户类型" prop="role" id="formItemRole">
            <el-select v-model="signUpForm.role" placeholder="请选择用户类型">
              <el-option label="学生" value="student"></el-option>
              <el-option label="官方人员" value="official"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="验证码" prop="captcha">
            <el-col id="login_captcha_input" :span="11">
              <el-input
                type="text"
                v-model="signUpForm.captcha"
                autocomplete="off"
                placeholder="请输入验证码"
              ></el-input>
            </el-col>
            <el-col :span="11">
              <img
                src="/api/captcha"
                alt="验证码"
                ref="captchaImg"
                @click="changeCaptcha()"
              />
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('signUpForm')"
              >注册</el-button
            >
            <el-button @click="resetForm('signUpForm')">重置</el-button>
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
  components: { Header },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length > 20 || value.length < 6) {
        callback(new Error("密码在6~20个字符之间"));
      } else {
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value != this.signUpForm.pass) {
        callback(new Error("两次输入密码不一致!"));
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
      signUpForm: {
        // username: "",
        // nickname: "",
        // sex: "男",
        // pass: "",
        // checkPass: "",
        // role: "student",
        // captcha: "",
        username: "987654",
        nickname: "张三",
        pass: "123123",
        checkPass: "123123",
        role: "student",
        captcha: "1234",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 6,
            max: 18,
            message: "长度在 6 到 18 个字符",
            trigger: "blur",
          },
        ],
        nickname: [
          { required: true, message: "请输入用户昵称", trigger: "blur" },
          {
            min: 1,
            max: 12,
            message: "长度在 1 到 12 个字符",
            trigger: "blur",
          },
        ],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        captcha: [{ validator: validateCaptcha, trigger: "input" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return false;
        }
        let data = {
          user: {
            username: this.signUpForm.username,
            password: this.signUpForm.pass,
            nickname: this.signUpForm.nickname,
            userRole: this.signUpForm.role,
          },
          captcha: this.signUpForm.captcha,
        };
        // 请求后端
        this.$axios({
          method: "post",
          url: "/api/users/register",
          data,
        })
          .then((response) => {
            console.log(response);
            if (response.data.code === 200) {
              // this.$router.push({
              //   name: "index",
              // });
            } else {
              this.renderResult(response.data);
            }
          })
          .catch((error) => {
            console.log(error);
            this.notifyException();
          });

        // 更新验证码
        this.changeCaptcha();
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    changeCaptcha() {
      util.changeCaptcha(this.$refs.captchaImg);
    },
  },
};
</script>

<style>
#signUpBody {
  margin: 50px 0 0 0;
  text-align: center;
}

.formTitle {
  font-size: 25px;
  font-weight: bold;
  letter-spacing: 20px;
  text-indent: 20px;
  text-align: center;
  margin: 0 0 30px 0;
}

#signUpFormItems {
  display: inline-block;
  width: 30%;
  margin: 0 81px 0 0;
}

#formItemRole > div > div {
  display: block;
}
</style>