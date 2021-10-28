<template>
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
    this.$axios
      .get(process.env.VUE_APP_BASE_API + "/slideshows/index")
      .then((response) => {
        console.log(response);
        if (response.data.code === 200) {
          let dataList = response.data.data;
          for (let item of dataList) {
            item.filePath =
              process.env.VUE_APP_BASE_API + "/slideshows/" + item.id;
          }
          this.slideshowDataList = dataList;
        } else {
          console.log(response.data);
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