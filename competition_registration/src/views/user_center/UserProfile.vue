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
        .get("/users/curUser")
        .then((response) => {
          console.log(response);
          if (response.code === 200) {
            let userData = response.data;
            this.userProfile.items = [
              ["用户昵称", userData.username],
              ["用户ID", userData.id],
              ["邮箱", userData.email],
              [
                "注册时间",
                this.dayjs(userData.registerTime).format("YYYY-MM-DD"),
              ],
            ];
            let studentData = userData.userInfo;
            this.studentProfile.items = [
              ["姓名", studentData.name],
              ["性别", studentData.sex ? "男" : "女"],
              ["生日", this.dayjs(studentData.birthday).format("YYYY-MM-DD")],
              ["学校", "广东金融学院"],
              ["专业", "软件工程"],
              [
                "入学年分",
                studentData.enrollmentDate == null
                  ? "未填写"
                  : this.dayjs(studentData.enrollmentDate).format("YYYY年MM月"),
              ],
            ];
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getCurUser();
  },
};
</script>

<style>
#userProfileBody {
  width: 1200px;
  margin: 35px auto;
}
</style>