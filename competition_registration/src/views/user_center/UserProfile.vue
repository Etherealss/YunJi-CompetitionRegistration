<template>
  <div id="userProfileBody">
    <ProfileItems :profile="userProfile" />
    <ProfileItems :profile="studentProfile" />
  </div>
</template>

<script>
import ProfileItems from "@/components/user_center/user_profile/ProfileItems";
// import StudentProfile from "@/components/user_center/user_profile/StudentProfile";
export default {
  components: { ProfileItems },
  data() {
    return {
      userProfile: {
        title: "用户信息",
        items: [
          ["用户昵称", "张小三"],
          ["用户ID", 1],
          ["邮箱", "1231231234@qq.com"],
          ["注册时间", this.dayjs(new Date()).format("YYYY-MM-DD")],
        ],
      },
      studentProfile: {
        title: "学生信息",
        items: [
          ["姓名", "张三"],
          ["性别", "男"],
          ["生日", this.dayjs(new Date()).format("YYYY-MM-DD")],
          ["学校", "广东金融学院"],
          ["专业", "软件工程"],
          ["入学年分", this.dayjs(new Date()).format("YYYY-MM-DD")],
        ],
      },
    };
  },
  methods: {
    getCurUser() {
      this.$axios
        .get(process.env.VUE_APP_BASE_API + "/users/curUser")
        .then((response) => {
          if (response.data.code === 200) {
            let userData = response.data.data;
            this.userProfile.items = [
              ["用户昵称", userData.username],
              ["用户ID", userData.id],
              ["邮箱", userData.email],
              ["注册时间", this.dayjs(userData.registerTime).format("YYYY-MM-DD")],
            ];
            console.log(this.userProfile.items);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getCurUser();
  }
};
</script>

<style>
#userProfileBody {
  width: 1200px;
  margin: 35px auto;
}
</style>