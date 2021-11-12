<template>
  <div>
    <el-table
      ref="multipleTable"
      :data="competitons"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="45"> </el-table-column>
      <el-table-column width="250" prop="name" label="赛事名称">
      </el-table-column>
      <el-table-column
        prop="organizationName"
        label="举办方"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="managerName"
        label="创建者"
        width="100"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        width="110"
        prop="consultingPhone"
        label="联系方式"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        width="110"
        label="参赛人数限制"
        show-overflow-tooltip
        :formatter="teamMemeberFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        sortable
        :formatter="timeFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="修改时间"
        sortable
        :formatter="timeFormatter"
      >
      </el-table-column>
      <el-table-column label="审核操作" width="210"
        ><template slot-scope="scope">
          <el-button size="mini" @click="handleReviewShowMore(scope.row)"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="success"
            v-show="scope.row.state == 1"
            @click="handleReview(scope.row, true)"
            >通过</el-button
          >
          <el-button
            size="mini"
            type="danger"
            v-show="scope.row.state == 1"
            @click="handleReview(scope.row, false)"
            >打回</el-button
          >
        </template></el-table-column
      >
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="curPage"
      :page-sizes="[8, 10, 20, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageTotalCount"
      id="reviewCompPagination"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      competitons: [],
      multipleSelection: [],
      pageSize: 10,
      pageTotalCount: 0,
      curPage: 1,
      pageCount: 1,
    };
  },

  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    /**
     * 时间的格式化器
     */
    timeFormatter(row) {
      return this.dayjs(row.createTime).format("YYYY-MM-DD HH:mm:ss");
    },
    /**
     * 人数的字符串格式化器
     */
    teamMemeberFormatter(row) {
      if (row.minTeamMember == row.maxTeamMember) {
        return row.minTeamMember + "人";
      } else {
        return row.minTeamMember + "到" + row.maxTeamMember + "人";
      }
    },
    /**
     * 点击详情按钮
     */
    handleReviewShowMore(row) {
      let url = "/competitions/" + row.id;
      if (row.state == 1) {
        url += "?state=1";
      }
      this.$doRoute(url);
    },
    /**
     * 执行审核操作（通过或打回）
     */
    handleReview(row, action) {
      this.$axios({
        method: "POST",
        url: "/admins/review/competitions",
        data: {
          competitionId: row.id,
          action: action,
        },
      }).then((r) => {
        if (r.code == 200) {
          this.$notify.success("操作成功！");
          this.getPageData();
        }
      });
    },
    /**
     * 获取页面数据
     */
    getPageData() {
      this.$axios
        .get(
          "/competitions/review/pages/" +
            this.curPage +
            ";offset=" +
            this.pageSize
        )
        .then((r) => {
          if (r.code == 200) {
            this.competitons = r.data.pageData;
            this.pageTotalCount = r.data.totalCount;
            this.pageCount = r.data.totalPage;
          }
        });
    },
    /**
     * 修改页面显示的数据量
     */
    handleSizeChange(val) {
      this.pageSize = val;
      this.getPageData();
    },
    /**
     * 点击分页按钮跳转页面
     */
    handleCurrentChange(val) {
      this.curPage = val;
      this.getPageData();
    },
  },
  mounted() {
    this.getPageData();
  },
};
</script>

<style>
#reviewCompPagination {
  float: right;
  margin: 15px 53px 0 0;
}
</style>