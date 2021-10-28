<template>
  <div>
    <el-menu
      mode="horizontal"
      :default-active="activeIndex"
      router
      style="overflow-x:auto;"
    >
      <NavMenu :navMenus="menuData" style="width:800px;overflow-x:auto;"></NavMenu>
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
      activeIndex: "account",
      menuData: [
        //帐号管理
        {
          entity: {
            id: 0,
            name: "accountManage",
            icon: "el-icon-setting",
            alias: "帐号管理",
          },
          childs: [
            {
              entity: {
                id: 2,
                name: "accountManage",
                icon: "el-icon-tickets",
                alias: "用户信息",
                value: "/users/profile",
              },
            },
            {
              entity: {
                id: 3,
                name: "emailManage",
                icon: "el-icon-message",
                alias: "邮箱管理",
                value: "/users/email",
              },
            },
            {
              entity: {
                id: 4,
                name: "passManage",
                icon: "el-icon-service",
                alias: "密码管理",
                value: "/users/pass",
              },
            },
          ],
        },
        //参赛管理
        {
          entity: {
            id: 5,
            name: "myCompetitions",
            icon: "el-icon-message",
            alias: "我的比赛",
          },
          //二级
          childs: [
            {
              entity: {
                id: 6,
                name: "compEntered",
                icon: "el-icon-loading",
                alias: "我参加的",
                value: { path: "/users/entered" },
              },
            },
            {
              entity: {
                id: 7,
                name: "compWatched",
                icon: "el-icon-star-on",
                alias: "我关注的",
                value: "/users/watched",
              },
            },
          ],
        },
        // 我的队伍
        {
          entity: {
            id: 8,
            name: "myTeam",
            icon: "el-icon-user",
            alias: "我的队伍",
            value: "/users/teams",
          },
        },
      ],
    };
  },
};
</script>

<style>
</style>

