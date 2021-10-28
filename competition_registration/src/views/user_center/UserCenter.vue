<template>
  <div>
    <Header />
    <div id="userCenterMenu">
      <UserCenterMenu />
    </div>
    <div id="userCenterBody">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Header from "@/components/header/Header";
import UserCenterMenu from "@/views/user_center/UserCenterMenu";
export default {
  components: { Header, UserCenterMenu },
};
</script>

<style>
#userCenterMenu {
  border-top: solid 1px #e6e6e6;
}

</style>