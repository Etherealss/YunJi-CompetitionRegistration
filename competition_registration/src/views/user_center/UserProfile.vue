<template>
  <div id="userProfileBody">
    <ProfileItems :profile="getUserProfile()" />
    <ProfileItems
      :profile="getUserRoleProfile()"
      v-if="!$store.getters.isAdmin"
    />
  </div>
</template>

<script>
import ProfileItems from "@/views/utils/ProfileItems";
export default {
  components: { ProfileItems },
  data() {
    return {
      user: {},
    };
  },
  methods: {
    getCurUser() {
      this.$axios
        .get("/users/curUser")
        .then((response) => {
          console.log(response);
          if (response.code === 200) {
            this.user = response.data;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getUserProfile() {
      let userData = this.user;
      let userProfile = {
        title: "用户信息",
        items: [
          ["用户昵称", userData.username],
          ["用户ID", userData.id],
          ["邮箱", userData.email],
          ["注册时间", this.dayjs(userData.registerTime).format("YYYY-MM-DD")],
        ],
      };
      return userProfile;
    },
    getUserRoleProfile() {
      let userRole = this.user.userRole;
      if (userRole == "student") {
        let studentData = this.user.userInfo;
        console.log(studentData);
        let studentProfile = {
          title: "学生信息",
          items: [
            ["姓名", studentData.name],
            ["性别", studentData.sex ? "男" : "女"],
            ["生日", this.dayjs(studentData.birthday).format("YYYY-MM-DD")],
            ["学校", studentData.schoolName],
            ["专业", studentData.major],
            [
              "入学年分",
              studentData.enrollmentDate == null
                ? "未填写"
                : this.dayjs(studentData.enrollmentDate).format("YYYY年MM月"),
            ],
          ],
        };
        return studentProfile;
      }
      if (userRole == "official") {
        let officialProfile = {
          title: "官方人员个人信息",
          items: [
            ["姓名", "张三"],
            ["性别", "男"],
            ["生日", this.dayjs(new Date()).format("YYYY-MM-DD")],
            ["自我介绍", "广东金融学院"],
            ["联系方式", "软件工程"],
          ],
        };
        return officialProfile;
      }
    },
  },
  beforeMount() {
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