<template>
  <div>
    <el-col class="teamItem" :span="5">
      <el-card class="box-card" shadow="hover">
        <!-- 卡片头部 -->
        <div slot="header" class="clearfix">
          <span class="teamName">{{ team.name }}</span>
          <!-- 修改按钮 -->
          <el-button
            @click="doModify()"
            style="float: right; padding: 5px"
            type="primary"
            icon="el-icon-edit"
            plain
            circle
          ></el-button>
        </div>
        <!-- 队伍信息 -->
        <div class="itemBody">
          <div class="leader">队长：{{ team.leader.userInfo.name }}</div>
          <div v-for="member in team.members" :key="member.id" class="member">
            {{ "队员：" + member.userInfo.name }}
          </div>
        </div>
      </el-card>
    </el-col>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  props: ["team"],
  methods: {
    doModify() {
      this.$parent.setTargetModifyTeam(this._props.team);
    },
  },
};
</script>

<style>
.teamItem {
  margin: 0 20px 20px 0;
  display: inline-block;
  cursor: pointer;
}

.teamName {
  font-weight: 800;
  font-size: 18px;
}
.itemBody {
  margin: 20px 15px;
}
.leader {
  margin: 0 0 20px 0;
  font-size: 14px;
  font-weight: 700;
}

.member {
  margin: 0 0 12px 0;
  font-size: 14px;
  font-weight: 500;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 250px;
  height: 230px;
}
#modifyForm {
  width: 90%;
}
#formButtons {
  display: flex;
  justify-content: space-evenly;
  padding: 0 86px;
}
</style>