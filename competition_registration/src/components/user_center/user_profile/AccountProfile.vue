<template>
  <el-card class="profileGroup" id="accountProfile">
    <div v-for="(item, index) in items" :key="index">
      <span class="itemName">{{ item[0] + "：" }}</span>
      <span class="itemValue">{{ item[1] }}</span>
    </div>
  </el-card>
</template>

<script>
export default {
  props: ["items"],
};
</script>

<style>
</style>