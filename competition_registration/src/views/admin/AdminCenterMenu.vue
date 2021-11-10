<template>
  <div>
    <el-menu
      default-active="1"
      router
      id="adminCenterMenu"
      active-text-color="rgb(14,200,218)"
    >
      <el-menu-item index="1" route="/admins/review/competitions">
        <i class="el-icon-medal-1"></i>
        <span slot="title">新比赛审核</span>
      </el-menu-item>
      <el-menu-item index="2" route="/admins/review/organizations">
        <i class="el-icon-user"></i>
        <span slot="title">新组织审核</span>
      </el-menu-item>
      <el-menu-item index="3" route="/admins/manage/permissions">
        <i class="el-icon-s-check"></i>
        <span slot="title">权限管理</span>
      </el-menu-item>
      <el-menu-item index="4" route="/admins/manage/competitions">
        <i class="el-icon-reading"></i>
        <span slot="title">赛事管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "competitions",
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style>
#adminCenterMenu {
  /* 让侧栏布满屏幕 height: calc(100vh - header高度) */
  height: calc(100vh - 65px);
}
</style>

