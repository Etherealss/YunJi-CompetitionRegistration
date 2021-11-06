<template>
  <!-- 右侧抽屉 -->
  <el-drawer
    title="报名比赛"
    :visible.sync="showCompRegisterDrawer"
    :before-close="cancelCompRegisterForm"
  >
    <div v-loading="compRegisterDrawerLoading">
      <!-- 比赛报名 表单 -->
      <el-form :model="compRegisterForm" id="compRegisterForm">
        <!-- 选择队伍 -->
        <el-form-item label="选择参赛队伍">
          <el-select v-model="compRegisterForm.teamId" placeholder="请选择">
            <el-option
              v-for="team in teams"
              :key="team.id"
              :label="team.name"
              :value="team.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div id="formButtons">
        <el-button @click="cancelCompRegisterForm">取 消</el-button>
        <el-button type="primary" @click="handleSave" :loading="loading">{{
          loading ? "提交中 ..." : "报 名"
        }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
export default {
  data() {
    return {
      // 父组件设置的数据
      teams: [],
      showCompRegisterDrawer: false,
      formLabelWidth: "100px",
      timer: null,
      // 修改表单的提交标志
      loading: false,
      compRegisterDrawerLoading: true,
      // 修改表单的数据
      compRegisterForm: {
        teamId: null,
      },
    };
  },
  methods: {
    /**
     * 父组件调用，传入team数据
     */
    doRegister() {
      this.compRegisterDrawerLoading = true;
      this.showCompRegisterDrawer = true;
      // 发送时间，用于计算请求响应时长
      let sendTime = new Date().getTime();
      this.$axios({
        methods: "get",
        url: "/teams/leadTeams",
      }).then((r) => {
        if (r.code == 200) {
          this.teams = r.data;
          let diffTime = new Date().getTime() - sendTime;
          if (diffTime > 1000) {
            // 请求时间超过1000ms
            this.compRegisterDrawerLoading = false;
          } else {
            // 请求时间过短，为了避免抖动，这里等待1000ms
            setTimeout(() => {
              this.compRegisterDrawerLoading = false;
            }, 1000);
          }
        }
      });
    },
    handleSave() {
      this.$confirm("确定报名", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.loading = true;
          console.log({
            teamId: this.compRegisterForm.teamId,
            competitionId: this.$route.params.id,
          });
          this.$axios({
            method: "POST",
            url: "/registrations/register",
            data: {
              teamId: this.compRegisterForm.teamId,
              competitionId: this.$route.params.id,
            },
          }).then((r) => {
            if (r.code == 200) {
              this.$notify.success("报名成功！");
            } else {
              this.$notify.success("出现错误！");
            }
          });
          this.$message({
            type: "success",
            message: "保存成功!",
          });
          this.showCompRegisterDrawer = false;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
          this.showCompRegisterDrawer = false;
        });
    },
    cancelCompRegisterForm() {
      this.loading = false;
      this.showCompRegisterDrawer = false;
      clearTimeout(this.timer);
    },
  },
};
</script>

<style>
#el-drawer__title span {
  font-weight: 600;
  font-size: 18px;
}

#compRegisterForm {
  padding: 0 0 0 40px;
}
</style>