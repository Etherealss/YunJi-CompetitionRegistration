<template>
  <div>
    <div class="notifyItemTitle">
      {{ notify.title }}
    </div>
    <div
      class="notifyItemDataList"
      v-for="(item, index) in notify.items"
      :key="index"
    >
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: ["notify"],
};
</script>

<style>
</style>