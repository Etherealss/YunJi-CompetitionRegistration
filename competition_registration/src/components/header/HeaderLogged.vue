<template>
  <div id="headerLoggedBody">
    <div id="notifyBtn" @click="doRoute('/notify/team')">
      <el-badge is-dot class="item">
        <el-button
          class="share-button"
          icon="el-icon-bell"
          size="small"
        ></el-button>
      </el-badge>
    </div>
    <div id="userHeaderAvatar" @click="doRoute('/users/profile')">
      <el-avatar :size="40" :src="avatar"></el-avatar>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      avatar: "",
    };
  },
  methods: {
    doRoute(path) {
      this.$router.push(path);
    },
    getAvatarData() {
      this.$axios({
        method: "post",
        url: "/users/avatar",
      }).then((response) => {
        // 显示base64图片
        this.avatar = "data:image/jpeg;base64," + response.data;
      });
    },
  },
  mounted() {
    let userDetails = this.$store.getters.getUserDetails;
    if (userDetails != null) {
      this.getAvatarData();
    }
  },
};
</script>

<style>
#headerLoggedBody {
  padding: 6px 0;
  display: flex;
  justify-content: space-around;
  margin: 0 100px 0 0;
}
#notifyBtn {
  display: inline-block;
  margin: 5px 20px 10px 0;
}
#userHeaderAvatar {
  display: inline-block;
  cursor: pointer;
}
</style>