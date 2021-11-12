<template>
  <div id="orgItemBody">
    <div v-for="orgItem in orgData" :key="orgItem.id">
      <ProfileItems :profile="getOrgProfile(orgItem)" />
    </div>
    <el-button @click="inputOrgId()">申请添加新组织</el-button>
  </div>
</template>

<script>
import ProfileItems from "@/views/utils/ProfileItems";
export default {
  name: "UserTeam",
  components: { ProfileItems },
  data() {
    return {
      orgData: [],
    };
  },
  methods: {
    getOrgProfile(org) {
      console.log(org);
      let orgProfile = {
        title: org.name + " 组织信息",
        items: [
          ["组织名称", org.name],
          ["组织简介", org.profile],
          ["所属院校", org.affiliatedSchool],
          ["负责人", org.managerName],
          ["联系方式", org.contactNumber],
        ],
      };
      return orgProfile;
    },
    inputOrgId() {
      this.$prompt("请输入组织id", "申请加入组织", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[0-9]+$/,
        inputErrorMessage: "格式不正确",
      }).then(({ value }) => {
        console.log(value);
        this.requestAddOrg(value);
      });
    },
    requestAddOrg(id) {
      this.$axios({
        method: "POST",
        url: "/organizations/add",
        data: id,
      }).then((r) => {
        if (r.code == 200) {
          this.$notify.success("申请成功");
        }
      });
    },
  },
  beforeMount() {
    // 初始化时自动加载页面数据
    this.$axios.get("/organizations/my").then((r) => {
      if (r.code == 200) {
        this.orgData = r.data;
      }
    });
  },
};
</script>

<style scoped>
#orgItemBody {
  width: 1200px;
  margin: 35px auto;
}
</style>