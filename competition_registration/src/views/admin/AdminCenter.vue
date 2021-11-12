<template>
  <div id="adminCenter">
    <Header />
    <el-container
      ><el-aside
        width="200px"
        id="adminCenterAside"
      >
        <AdminCenterMenu />
      </el-aside>
      <el-main id="adminBody"> <router-view></router-view> </el-main
    ></el-container>
  </div>
</template>

<script>
import Header from "@/components/header/Header";
import AdminCenterMenu from "@/views/admin/AdminCenterMenu";
export default {
  components: { Header, AdminCenterMenu },
};
</script>

<style>
#adminCenter {
  background: rgb(255, 255, 255);
}
#adminCenterAside {
  position: -webkit-sticky;
  position: sticky;
  top: 65px;
  left: 0;
  height: calc(100% - 65px);
}
</style>