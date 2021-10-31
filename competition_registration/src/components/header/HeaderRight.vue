<template>
  <div>
    <div id="headerSign" v-show="userUnloggedFLag">
      <HeaderSign />
    </div>
    <div v-show="!userUnloggedFLag" id="headerLogged">
      <HeaderLogged />
    </div>
  </div>
</template>

<script>
import HeaderSign from "@/components/header/HeaderSign";
import HeaderLogged from "@/components/header/HeaderLogged";
export default {
  data() {
    return {
    };
  },
  components: {
    HeaderSign,
    HeaderLogged,
  },
  computed: {
    userUnloggedFLag() {
      // console.log('userUnloggedFLag:', this.$store.getters.getUserDetails);
      return this.$store.getters.getUserDetails == null;
    },
  },
};
</script>

<style>
.headerLogged {
  display: inline-block;
  margin: 10px;
}
</style>