<template>
  <div id="adminCenter">
    <Header />
    <el-container
      ><el-aside
        width="200px"
        id="adminCenterAside"
        style="background-color: #545c64"
      >
        <AdminCenterMenu />
      </el-aside>
      <el-main id="adminBody">
        <router-view></router-view> </el-main
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
</style>