<template>
  <div>
    <div>
      <el-col class="teamItem" :span="6">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span class="teamName">创建/加入新队伍</span>
          </div>
          <div id="addButtonBox">
            <el-button
              type="primary"
              icon="el-icon-circle-plus-outline"
              @click="createNewTeam()"
              plain
              >创建新队伍</el-button
            >
            <el-button
              type="primary"
              icon="el-icon-search"
              @click="inputInviteCode()"
              plain
              >通过邀请码加入队伍</el-button
            >
          </div>
        </el-card>
      </el-col>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    inputInviteCode() {
      this.$prompt("请输入邀请码", "加入现有队伍", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[A-Za-z0-9]{32}$/,
        inputErrorMessage: "格式不正确",
      }).then(({ value }) => {
        console.log(value);
        this.addByInviteCode(value);
      });
    },
    createNewTeam() {
      // 信息检验
      this.$prompt("请输入队伍名称（3到15字）", "创建新队伍", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[\u4E00-\u9FA5A-Za-z0-9_]{3,15}$/,
        inputErrorMessage: "格式不正确",
      }).then(({ value }) => {
        console.log(value);
        // 发送请求 创建队伍
        this.$axios({
          method: "post",
          url: "/teams/create",
          data: value,
        })
          .then((response) => {
            // 响应
            if (response.code === 200) {
              // 子组件调用父组件的方法，刷新页面
              this.$emit("getTeamData");
              this.$notify({
                title: "创建成功",
                message: "队伍 " + value + " 创建成功！",
                type: "success",
                duration: 2000,
              });
            } else {
              this.renderResult(response);
              this.$notify.error({
                title: "创建失败",
                message: "队伍 " + value + " 创建失败！",
                duration: 2000,
              });
            }
          })
          .catch((error) => {
            console.log(error);
            this.$notify({
              title: "创建失败",
              message: "队伍 " + value + " 创建失败！",
              type: "error",
              duration: 2000,
            });
          });
      });
    },
    /**
     * 发送请求 加入队伍
     */
    addByInviteCode(inviteCode) {
      this.$axios({
        method: "post",
        url: "/teams/addByInviteCode",
        data: inviteCode,
      }).then((resp) => {
        if (resp.code == 200) {
          this.$notify.success({
            title: "申请发送成功",
            message: "申请发送成功！",
            duration: 2000,
          });
        } else if (resp.code == 404) {
          this.$notify.warning({
            title: "申请失败",
            message: "邀请码不存在",
            duration: 2000,
          });
        } else if (resp.code == 10501) {
          this.$notify.warning({
            title: "申请失败",
            message: "你已在队伍中",
            duration: 2000,
          });
        }
      });
    },
  },
};
</script>

<style>
.teamItem {
  margin: 0 20px 20px 0;
  display: inline-block;
}

.teamName {
  font-weight: 800;
  font-size: 18px;
}
.itemBody {
  margin: 20px 15px;
}
.leader {
  margin: 0 0 20px 0;
  font-size: 14px;
  font-weight: 700;
}

.member {
  margin: 0 0 12px 0;
  font-size: 14px;
  font-weight: 500;
}

#addButtonBox {
  margin: 20px auto;
}

#addButtonBox button {
  display: block;
  width: 180px;
  margin: 0 auto;
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 250px;
  height: 230px;
}
</style>