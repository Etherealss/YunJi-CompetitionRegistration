<template>
  <header class="container-fluid" id="headerBody">
    <div id="systemNameBox" @click="toIndex()">
      <span>云&nbsp;&nbsp;&nbsp;&nbsp;集</span>
    </div>
    <div id="headerPartition">
      <ul>
        <li href="#">校内赛事</li>
        <li href="#">全部赛事</li>
        <li href="#">组队广场</li>
      </ul>
    </div>

    <span id="headerSearch">
      <HeaderSearch />
    </span>

    <HeaderRight id="headerRight" />
  </header>
</template>

<script>
import HeaderSearch from "@/components/header/HeaderSearch";
import HeaderRight from "@/components/header/HeaderRight";
export default {
  components: {
    HeaderSearch,
    HeaderRight,
  },
  data() {
    return {
      userLoggedFLag: false,
    };
  },
  methods: {
    toIndex() {
      this.$router.push("/index");
    },
  },
};
</script>

<style>
header {
  background: #ffffff;
  padding: 5px 0 5px 0;
  height: 65px;
  min-width: 1000px;
  box-shadow: 1px 1px 10px rgb(0 0 0 / 10%);
}

#systemNameBox span {
  color: #78e0d1;
  font-size: 2.3em;
  font-family: "方正粗黑宋简体", "楷书", "微软雅黑", serif;
  cursor: pointer;
  margin: 0 0 0 20px;
}
#systemNameBox {
  margin: 5px auto;

  float: left;
}

#userInfoBox {
  float: right;

  text-align: right;
  /*用户名居中效果*/
  margin: 9px 50px 0 0;
}

#userInfoBox div .dropdown-menu {
  min-width: 96px;
}

#header_usernameBtn {
  margin: auto;
}

#headerPartition {
  display: inline-block;
}

#headerPartition > ul {
  /* 一行 */
  display: flex;
  /* 无序列表没有原点 */
  list-style-type: none;
  margin: 15px 0 0 0;
}

#headerPartition > ul > li {
  padding: 0 20px;
  display: inline-block;
  text-decoration: none;
  font-size: 18px;
}

#headerSearch {
  margin: 0 0 0 50px;
  width: 50%;
}

#headerRight {
  float: right;
}
</style>