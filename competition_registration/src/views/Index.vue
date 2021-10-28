<template>
  <div>
    <Header />
    <div id="indexBody" class=".page-component__scroll">
    <div itemid="indexSlideshow">
      <IndexSlide />
    </div>
    <div id="stickyArea">
      <div id="indexContent">
        <IndexContent />
      </div>
      <div id="indexAside">
        <IndexAside />
      </div>
    </div>

    <el-backtop target="#indexContent" :bottom="100" :visibility-height="20">
      <img src="@/assets/icon/up.png" />
    </el-backtop>
  </div>
  </div>
  
</template>

<script>
import Header from "@/components/header/Header";
import IndexContent from "@/components/index/IndexContent";
import IndexSlide from "@/components/index/IndexSlide";
import IndexAside from "@/components/index/IndexAside";
export default {
  name: "Index",
  components: { Header, IndexContent, IndexSlide, IndexAside },
};
</script>

<style>
#indexBody {
  width: 1200px;
  margin: 35px auto;
  overflow-y: auto;
  min-height: 1000px;
}

#stickyArea {
  display: flex;
  justify-content: flex-start;
}

/* #indexContent {

} */

#indexAside {
  position: -webkit-sticky;
  position: sticky;
  top: 5px;
  left: 1000px;
}
/* #indexContent {
  float: left;
} */

/* 清除浮动塌陷 */
/* #indexContext,
#stickyArea :after {
  content: "";
  height: 0;
  line-height: 0;
  display: block;
  visibility: hidden;
  clear: both;
} */

</style>