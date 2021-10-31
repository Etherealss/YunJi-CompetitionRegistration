<template>
  <div id="userItemBody">
    <div v-for="teamItem in teamData" :key="teamItem.id">
      <UserTeamItem :team="teamItem" />
    </div>
    <UserAddTeamItem />
    <UserModifyTeam ref="userModifyTeam" />
  </div>
</template>

<script>
import UserTeamItem from "@/components/user_center/user_team/UserTeamItem";
import UserAddTeamItem from "@/components/user_center/user_team/UserAddTeamItem";
import UserModifyTeam from "../../components/user_center/user_team/UserModifyTeam.vue";
export default {
  name: "UserTeam",
  components: { UserTeamItem, UserAddTeamItem, UserModifyTeam },
  data() {
    return {
      teamData: [
        /*
        {
          id: 1,
          name: "你说的都队1",
          time: "2021-10-21",
          leader: {
            id: 1,
            name: "张三",
            userDetails: {
              id: 1,
              username: "1231231234@qq.com",
              nickname: "张小三",
            },
          },
          members: [
            {
              id: 2,
              name: "李四",
              userDetails: {
                id: 2,
                username: "1231231235@qq.com",
                nickname: "李小四",
              },
            },
            {
              id: 3,
              name: "王五",
              userDetails: {
                id: 3,
                username: "1231231236@qq.com",
                nickname: "王小五",
              },
            },
          ],
        },
        {
          id: 2,
          name: "你说的都队2",
          time: "2021-10-21",
          leader: {
            id: 1,
            name: "张三",
            userDetails: {
              id: 1,
              username: "1231231234@qq.com",
              nickname: "张小三",
            },
          },
          members: [
            {
              id: 2,
              name: "李四",
              userDetails: {
                id: 2,
                username: "1231231235@qq.com",
                nickname: "李小四",
              },
            },
            {
              id: 3,
              name: "王五",
              userDetails: {
                id: 3,
                username: "1231231236@qq.com",
                nickname: "王小五",
              },
            },
          ],
        },
        {
          id: 3,
          name: "你说的都队3",
          time: "2021-10-21",
          leader: {
            id: 1,
            name: "张三",
            userDetails: {
              id: 1,
              username: "1231231234@qq.com",
              nickname: "张小三",
            },
          },
          members: [
            {
              id: 2,
              name: "李四",
              userDetails: {
                id: 2,
                username: "1231231235@qq.com",
                nickname: "李小四",
              },
            },
            {
              id: 3,
              name: "王五",
              userDetails: {
                id: 3,
                username: "1231231236@qq.com",
                nickname: "王小五",
              },
            },
          ],
        },
        {
          id: 4,
          name: "你说的都队4",
          time: "2021-10-21",
          leader: {
            id: 1,
            name: "张三",
            userDetails: {
              id: 1,
              username: "1231231234@qq.com",
              nickname: "张小三",
            },
          },
          members: [
            {
              id: 2,
              name: "李四",
              userDetails: {
                id: 2,
                username: "1231231235@qq.com",
                nickname: "李小四",
              },
            },
            {
              id: 3,
              name: "王五",
              userDetails: {
                id: 3,
                username: "1231231236@qq.com",
                nickname: "王小五",
              },
            },
          ],
        },
        {
          id: 5,
          name: "你说的都队5",
          time: "2021-10-21",
          leader: {
            id: 1,
            name: "张三",
            userDetails: {
              id: 1,
              username: "1231231234@qq.com",
              nickname: "张小三",
            },
          },
          members: [
            {
              id: 2,
              name: "李四",
              userDetails: {
                id: 2,
                username: "1231231235@qq.com",
                nickname: "李小四",
              },
            },
            {
              id: 3,
              name: "王五",
              userDetails: {
                id: 3,
                username: "1231231236@qq.com",
                nickname: "王小五",
              },
            },
          ],
        },
      */
      ],
    };
  },
  methods: {
    /**
     * 获取队伍数据
     */
    getTeamData() {
      this.$axios.get("/teams/myTeams").then((data) => {
        if (data.code == 200) {
          this.teamData = data.data;
        }
      });
    },
    /**
     * 想子组件传数据，打开侧面抽屉
     */
    setTargetModifyTeam(team) {
      this.$refs.userModifyTeam.doModify(team);
    }
  },
  mounted() {
    this.getTeamData();
  },
};
</script>

<style>
#userItemBody {
  width: 1200px;
  margin: 35px auto;
}
</style>