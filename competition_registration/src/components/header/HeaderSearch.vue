<template>
  <!-- 搜索框 https://element.eleme.cn/#/zh-CN/component/input -->
      <el-autocomplete
        class="inline-input"
        v-model="state2"
        :fetch-suggestions="querySearch"
        placeholder="搜索赛事..."
        :trigger-on-focus="false"
        @select="handleSelect"
      ></el-autocomplete>
</template>

<script>
export default {
  data() {
    return {
      restaurants: [],
      state1: "",
      state2: "",
    };
  },
  methods: {
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createFilter(queryString))
        : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (
          restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
          0
        );
      };
    },
    loadAll() {
      return [
        { value: "三全鲜食（北新泾店）", address: "长宁区新渔路144号" },
        {
          value: "Hot honey 首尔炸鸡（仙霞路）",
          address: "上海市长宁区淞虹路661号",
        },
        {
          value: "新旺角茶餐厅",
          address: "上海市普陀区真北路988号创邑金沙谷6号楼113",
        },
      ];
    },
    handleSelect(item) {
      console.log(item);
    },
  },
  mounted() {
    // 获取数据
    this.restaurants = this.loadAll();
  },
};
</script>

<style>
.el-autocomplete {
  width: 300px;
}
</style>