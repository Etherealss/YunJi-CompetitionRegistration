<template>
  <div>
    <Header />
    <div id="compDetailsBody">
      <competiton-content id="competitonContent" />
      <competition-aside id="competitonAside" v-show="canShowRegister()" />
    </div>
    <register-competition ref="registerCompetition" />
  </div>
</template>

<script>
import Header from "@/components/header/Header.vue";
import CompetitionAside from "./CompetitionAside.vue";
import CompetitonContent from "./CompetitonContent.vue";
import RegisterCompetition from "./RegisterCompetition.vue";
export default {
  components: {
    CompetitonContent,
    CompetitionAside,
    Header,
    RegisterCompetition,
  },
  methods: {
    toRegister() {
      this.$refs.registerCompetition.doRegister();
    },
    canShowRegister() {
      let userRole = this.$store.getters.getUserDetails.userRole;
      return userRole == null || userRole == "student";
    },
  },
};
</script>

<style>
#compDetailsBody {
  width: 1200px;
  margin: 35px auto;
  /* overflow-y: auto; */
  min-height: 1000px;
  display: flex;
}

#competitonContent {
  width: 857px;
}

#competitonAside {
  position: sticky;
  height: 200px;
  width: 215px;
  left: 1072px;
  top: 122px;
  border-radius: 5px;
  box-shadow: 1px 1px 10px rgb(204 204 204 / 60%);
  padding: 20px 0 0 0;
}
</style>