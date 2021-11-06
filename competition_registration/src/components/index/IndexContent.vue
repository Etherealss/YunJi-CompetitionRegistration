<template>
  <div id="competitions">
    <ul>
      <li v-for="item in page.pageData" :key="item.id">
        <IndexCompetition :comp="item" />
      </li>
    </ul>
  </div>
</template>

<script>
import IndexCompetition from "@/components/index/IndexCompetition";
export default {
  data() {
    return {
      page: {
        currentPage: 0,
        rows: 0,
        start: 0,
        totalCount: 0,
        totalPage: 0,
        pageData: [],
      },
    };
  },
  components: { IndexCompetition },
  methods: {
    getPageData(curPage) {
      this.$axios
        .get("/competitions/public/pages/" + curPage)
        .then((response) => {
          if (response.code === 200) {
            // 追加
            this.updatePage(response.data);
          } else {
            console.log(response);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    loadPageData() {
      if (this.page.currentPage < this.page.totalPage) {
        this.getPageData(this.page.currentPage + 1);
      }
    },
    updatePage(data) {
      //   console.log("更新页面：this.page.currentPage = " + this.page.currentPage);
      this.page.currentPage = data.currentPage;
      this.page.rows = data.rows;
      this.page.start = data.start;
      this.page.totalCount = data.totalCount;
      this.page.totalPage = data.totalPage;
      this.page.pageData.push.apply(this.page.pageData, data.pageData);
    },
  },
  mounted() {
    this.getPageData(1);
  },
};
</script>

<style>
/* #competitions {
  float: left;
} */

#competitions ul li {
  list-style-type: none;
}
</style>