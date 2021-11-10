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
            @click="handleReview(scope.row, true)"
            >通过</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleReview(scope.row, false)"
            >打回</el-button
          >
        </template></el-table-column
      >
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      competitons: [],
      multipleSelection: [],
    };
  },

  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    timeFormatter(row) {
      return this.dayjs(row.createTime).format("YYYY-MM-DD HH:mm:ss");
    },
    teamMemeberFormatter(row) {
      if (row.minTeamMember == row.maxTeamMember) {
        return row.minTeamMember + "人";
      } else {
        return row.minTeamMember + "到" + row.maxTeamMember + "人";
      }
    },
    handleReviewShowMore(row) {
      this.$doRoute("/competitions/" + row.id + "?state=1");
    },
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
        }
      });
    },
  },
  beforeMount() {
    this.$axios.get("/competitions/review").then((r) => {
      if (r.code == 200) {
        this.competitons = r.data;
      }
    });
  },
};
</script>

<style>
</style>