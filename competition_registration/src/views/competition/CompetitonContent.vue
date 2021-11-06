<template>
  <div>
    <!-- 比赛名称,比赛简介profile,最终修改时间update_time -->
    <el-card class="compDetailsBox" id="compProfile" shadow="never">
      <h2>{{ comp.name }}</h2>
      <p class="compTextDetail">{{ comp.profile }}</p>
      <div id="compProfileTime">
        {{ dayjs(comp.createTime).format("YYYY年MM月DD日") }}发布，最终修改于{{
          dayjs(comp.updateTime).format("YYYY年MM月DD日 HH:mm:ss")
        }}
      </div>
    </el-card>
    <!-- 比赛流程介绍process_introduce -->
    <el-card class="compDetailsBox" id="compProcess" shadow="never">
      <div class="compTitle">比赛流程介绍</div>
      <p class="compTextDetail">
        {{ comp.processIntroduce }}
      </p>
    </el-card>
    <!-- 举办方organization，负责人 manager，联系方式consulting_phone -->
    <el-card class="compDetailsBox" id="compProcess" shadow="never">
      <el-descriptions title="举办方信息">
        <el-descriptions-item label="举办方" labelClassName="compDescLable">{{
          comp.organizationName
        }}</el-descriptions-item>
        <el-descriptions-item label="负责人" labelClassName="compDescLable">{{
          comp.managerName
        }}</el-descriptions-item>
        <el-descriptions-item label="联系方式" labelClassName="compDescLable">{{
          comp.consultingPhone
        }}</el-descriptions-item>
        <el-descriptions-item
          label="比赛报名范围"
          labelClassName="compDescLable"
        >
          <el-tag size="small">{{ comp.scope }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item
          label="参赛人数限制"
          labelClassName="compDescLable"
          >{{ getTeamMemberLimit() }}</el-descriptions-item
        >
      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      comp: null,
    };
  },
  methods: {
    getTeamMemberLimit() {
      if (this.comp.maxTeamMember == 1) {
        return "单人参赛";
      } else {
        return (
          this.comp.minTeamMember + "到" + this.comp.maxTeamMember + "人参赛"
        );
      }
    },
  },
  beforeMount() {
    this.$axios({
      method: "get",
      url: "/competitions/public/" + this.$route.params.id,
    }).then((r) => {
      if (r.code == 200) {
        this.comp = r.data;
      } else if (r.code == 404) {
        this.$notify.error({
          title: "错误",
          message: "比赛记录不存在",
        });
      } else if (r.code == 4001) {
        // url参数缺失
        this.$notify.error({
          title: "错误",
          message: "访问方式出错！请回到主页面再次尝试！",
        });
      }
    });
  },
};
</script>

<style>
.compTitle {
  font-size: 17px;
  font-weight: 700;
  margin: 0 0 20px 0;
}
.compDescLable {
  font-weight: 550;
}
.compDetailsBox {
  width: 859px;
  margin: 20px 0;
  padding: 20px 30px 20px 30px;
}

.compDetailsBox h2 {
  display: inline-block;
  color: #343434;
  font-size: 21px;
  font-weight: 700;
  letter-spacing: 2px;
  margin-bottom: 16px;
}

.compTextDetail {
  /* 首行缩进 */
  text-indent: 31px;
  /* 行高 */
  line-height: 2;
  margin: 10px 10px 20px 0;
  font-size: 15px;
  font-weight: 400;
  /* 最小高度 */
  min-height: 40px;
  color: rgb(12, 12, 12);
}

.el-descriptions__title {
  font-size: 17px;
}

#compProfileTime {
  text-align: right;
  color: #8a8a8a;
}
</style>