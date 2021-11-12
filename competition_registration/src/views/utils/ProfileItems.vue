<template>
  <div>
    <el-card class="profileBody" shadow="never">
      <div class="itemsTitle">
        {{ profile.title }}
      </div>
      <div
        class="profileGroup"
        v-for="(item, index) in profile.items"
        :key="index"
      >
        <span class="itemName">{{ item[0] + "：" }}</span>
        <span class="itemValue">{{ item[1] }}</span>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  props: ["profile"],
};
</script>

<style scoped>
.profileBody {
  padding: 20px;
  margin-bottom: 20px;
}
.itemsTitle {
  font-size: 22px;
  font-weight: 800;
  padding: 7px 0 0 20px;
}
.profileGroup {
  padding: 20px 0 10px 25px;
}

.itemName {
  font-weight: 600;
  /* 设置最小宽度，对齐 */
  display: inline-block;
  min-width: 74px;
}
</style>