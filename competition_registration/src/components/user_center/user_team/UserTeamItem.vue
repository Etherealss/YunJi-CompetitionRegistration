<template>
  <el-col class="teamItem" :span="5">
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <span class="teamName">{{ team.name }}</span>
        <el-button style="float: right; padding: 5px" type="primary" icon="el-icon-edit" plain circle></el-button>
      </div>
      <div class="itemBody">
        <div class="leader">队长：{{ team.leader.name }}</div>
        <div v-for="member in team.members" :key="member.id" class="member">
          {{ "队员：" + member.name }}
        </div>
      </div>
    </el-card>
  </el-col>
</template>

<script>
export default {
  props: ["team"],
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
</style>