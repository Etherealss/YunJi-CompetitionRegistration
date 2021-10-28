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
        .get(
          process.env.VUE_APP_BASE_API + "/competitions/pages/" + curPage,
          {},
          {
            headers: {
              authorization: "Bearer bbbb00a2-cf9b-4296-9b3f-165e7a31568d",
            },
          }
        )
        .then((response) => {
          console.log(response);
          if (response.data.code === 200) {
            // 追加
            this.updatePage(response.data.data);
          } else {
            console.log(response.data);
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
#competitions {
  float: left;
}

#competitions ul li {
  list-style-type: none;
}
</style>