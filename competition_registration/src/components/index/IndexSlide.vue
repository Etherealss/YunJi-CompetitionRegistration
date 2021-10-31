<template>
  <!-- 首页轮播图：获取数据 -->
  <Slideshow :dataList="slideshowDataList" />
</template>

<script>
import Slideshow from "@/components/index/Slideshow";
export default {
  data() {
    return {
      slideshowDataList: [],
    };
  },
  components: {
    Slideshow,
  },
  methods: {},
  beforeCreate() {
    this.$axios({
      method: "get",
      url:"/slideshows/index",
      headers: {
        authorization: this.$store.getters.getToken,
      },
    })
      .then((response) => {
        if (response.code === 200) {
          let dataList = response.data;
          for (let item of dataList) {
            item.filePath = process.env.VUE_APP_BASE_API + "/slideshows/" + item.id;
          }
          this.slideshowDataList = dataList;
        } else {
          console.log(response);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style>
</style>