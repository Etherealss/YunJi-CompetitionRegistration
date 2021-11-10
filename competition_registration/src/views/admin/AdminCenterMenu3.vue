<template>
  <div>
    <el-menu
      mode="horizontal"
      :default-active="activeIndex"
      router
      style="overflow-x: auto"
    >
      <NavMenu
        :navMenus="menuData"
        style="width: 800px; overflow-x: auto"
      ></NavMenu>
    </el-menu>
  </div>
</template>

<script>
import NavMenu from "@/views/utils/NavMenu.vue";
export default {
  components: {
    NavMenu,
  },
  data() {
    return {
      activeIndex: "competitions",
      menuData: [
        // 新比赛审核
        {
          entity: {
            id: 0,
            name: "competitions",
            icon: "el-icon-medal-1",
            alias: "新比赛审核",
            value: "/admins/review/competitions",
          },
        }, 
        // 新组织审核
        {
          entity: {
            id: 1,
            name: "organizations",
            icon: "el-icon-user",
            alias: "新组织审核",
            value: "/admins/review/organizations",
          },
        },
        // 权限管理
        {
          entity: {
            id: 2,
            name: "competitions",
            icon: "el-icon-s-check",
            alias: "权限管理",
            value: "/admins/manage/permissions",
          },
        },
        // 赛事管理
        {
          entity: {
            id: 3,
            name: "myTeam",
            icon: "el-icon-reading",
            alias: "赛事管理",
            value: "/admins/manage/competitions",
          },
        },
      ],
    };
  },
};
</script>

<style>
</style>

