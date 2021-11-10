<template>
  <div>
    <Header />
    <div id="createCompBody">
      <el-form
        ref="createCompForm"
        id="createCompForm"
        :model="createCompForm"
        label-width="80px"
      >
        <el-form-item label="赛事名称">
          <el-input v-model="createCompForm.name"></el-input>
        </el-form-item>
        <el-form-item label="赛事简介">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 8 }"
            v-model="createCompForm.profile"
          ></el-input>
        </el-form-item>
        <el-form-item label="比赛流程">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 8 }"
            v-model="createCompForm.processIntroduce"
          ></el-input>
        </el-form-item>
        <el-form-item label="举办方">
          <el-select
            v-model="createCompForm.organizationId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in organizations"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="比赛范围">
          <el-input v-model="createCompForm.scope"></el-input>
        </el-form-item>
        <el-form-item label="队伍人数">
          <div>
            最小人数
            <el-input-number
              v-model="createCompForm.minTeamMember"
              :min="1"
              label="最小人数"
            ></el-input-number>
          </div>
          <div>
            最大人数
            <el-input-number
              v-model="createCompForm.maxTeamMember"
              :min="1"
              label="最大人数"
            ></el-input-number>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createComp">立即创建</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "@/components/header/Header.vue";
export default {
  components: { Header },
  data() {
    return {
      organizations: [],
      createCompForm: {
        name: "",
        profile: "",
        processIntroduce: "",
        scope: "",
        minTeamMember: 1,
        maxTeamMember: 1,
        organizationId: "",
      },
    };
  },
  methods: {
    createComp() {
      // console.log(this.createCompForm);
      this.$axios({
        method: "POST",
        url: "/competitions/create",
        data: this.createCompForm,
      }).then((r) => {
        if (r.code == 200) {
          this.$notify.success("发布成功！");
          this.$doRoute("/index");
        }
      });
    },
  },
  beforeMount() {
    this.$axios({
      method: "get",
      url: "/organizations/my",
    }).then((r) => {
      if (r.code == 200) {
        this.organizations = r.data;
      }
    });
  },
};
</script>

<style>
#createCompBody {
  width: 1200px;
  margin: 50px auto;
  min-height: 1000px;
  padding: 0 100px;
}
</style>