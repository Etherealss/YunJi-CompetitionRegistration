<template>
  <div>
    <Header />
    <div id="notifyBody">
      <NotifyAside id="asideBody" />
      <div id="routerViewBody">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/header/Header";
import NotifyAside from "./NotifyAside";
export default {
  components: { Header, NotifyAside },
};
</script>

<style>
#notifyBody {
  width: 1200px;
  margin: 16px auto 0;
  /* border: 1px red solid; */
}

#asideBody {
  float: left;
  width: 190px;
  border-radius: 2px;
  box-shadow: 1px 1px 10px rgb(204 204 204 / 60%);
}

#routerViewBody {
  position: relative;
  margin-left: 221px;
  background-color: #fff;
  box-shadow: 1px 1px 10px rgb(204 204 204 / 60%);
  padding: 24px;
  border-radius: 2px;
  min-height: 510px;
}
</style>