<template>
  <div>
    <el-carousel :interval="4000" type="card" height="300px">
      <el-carousel-item v-for="item in dataList" :key="item.id">
        <img
          :src="item.filePath"
          @click="toSlideshowPage(item.targetUrl)"
          :alt="item.info"
          style="width: 100%; height: auto;"
        />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  props: {
    dataList: {
      type: Array,
      require: true,
    },
  },
  methods: {
    toSlideshowPage(targetUrl) {
      this.$router.push(targetUrl);
    },
  },
};
</script>


<style>
.el-carousel__item {
  width: "533.33px";
  height: "300px";
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>