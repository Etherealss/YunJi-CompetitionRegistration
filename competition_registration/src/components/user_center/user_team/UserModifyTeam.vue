<template>
  <!-- 右侧抽屉 -->
  <el-drawer
    title="修改队伍信息"
    :visible.sync="showModifyDrawer"
    :before-close="cancelForm"
  >
    <div>
      <!-- 修改队伍 表单 -->
      <el-form :model="modifyForm" id="modifyForm">
        <!-- 队名 -->
        <el-form-item label="队伍名称" :label-width="formLabelWidth">
          <el-input v-model="modifyForm.teamName" autocomplete="off"></el-input>
        </el-form-item>
        <!-- 邀请码 -->
        <el-form-item label="邀请码" :label-width="formLabelWidth">
          {{ team.inviteCode }}
        </el-form-item>
        <!-- 队伍成员 无 -->
        <el-form-item
          label="队伍成员"
          :label-width="formLabelWidth"
          v-show="team.members.length == 0"
        >
          无
        </el-form-item>
        <!-- 队伍成员 多选框 -->
        <el-form-item
          label="队伍成员"
          :label-width="formLabelWidth"
          v-show="team.members.length > 0"
        >
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
            >全选</el-checkbox
          >
          <div style="margin: 15px 0"></div>
          <el-checkbox-group
            v-model="modifyForm.checkedMembers"
            @change="handleCheckedMembersChange"
          >
            <el-checkbox
              v-for="member in team.members"
              :key="member.id"
              :label="member.id"
              >{{ member.userInfo.name }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div id="formButtons">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button type="primary" @click="handleSave" :loading="loading">{{
          loading ? "提交中 ..." : "修 改"
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
      team: {
        name: "",
        members: [],
      },
      showModifyDrawer: false,
      // 多选框数据
      checkAll: false,
      isIndeterminate: false,
      formLabelWidth: "100px",
      timer: null,
      // 修改表单的提交标志
      loading: false,
      // 修改表单的数据
      modifyForm: {
        teamName: "",
        // 已选
        checkedMembers: [],
      },
    };
  },
  methods: {
    /**
     * 父组件调用，传入team数据
     */
    doModify(team) {
      this.showModifyDrawer = true;
      this.team = team;
      this.modifyForm.teamName = this.team.name;
    },
    /**
     * 点击“全选”按钮时的操作
     * 全选时val为true，取消全选为false
     */
    handleCheckAllChange(val) {
      // 通过改变 this.modifyForm.checkedMembers 来控制单选
      if (val) {
        let checkedIds = [];
        // 不能使用 for in，否则item为变成下标
        for (let item of this.team.members) {
          checkedIds.push(item.id);
        }
        // 获取数组中每个对象的id字段
        this.modifyForm.checkedMembers = val ? checkedIds : [];
        this.isIndeterminate = false;
      } else {
        this.modifyForm.checkedMembers = [];
      }
    },
    /**
     * value为当前选中的元素数组
     */
    handleCheckedMembersChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.team.members.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.team.members.length;
    },
    handleSave() {
      this.$confirm("保存修改并关闭？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.loading = true;
          this.$message({
            type: "success",
            message: "保存成功!",
          });
          this.modifyDrawer = false;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
          this.modifyDrawer = false;
        });
    },
    cancelForm() {
      this.loading = false;
      this.showModifyDrawer = false;
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
</style>