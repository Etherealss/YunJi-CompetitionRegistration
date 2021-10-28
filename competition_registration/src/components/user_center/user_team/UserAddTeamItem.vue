<template>
  <div>
    <div>
      <el-col class="teamItem" :span="6">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span class="teamName">创建/加入新队伍</span>
          </div>
          <div id="addButtonBox">
            <el-button
              type="primary"
              icon="el-icon-circle-plus-outline"
              @click="createNewTeam()"
              plain
              >创建新队伍</el-button
            >
            <el-button
              type="primary"
              icon="el-icon-search"
              @click="inputInviteCode()"
              plain
              >通过邀请码加入队伍</el-button
            >
          </div>
        </el-card>
      </el-col>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    inputInviteCode() {
      this.$prompt("请输入邀请码", "加入现有队伍", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[A-Za-z0-9]+$/,
        inputErrorMessage: "格式不正确",
      }).then(({ value }) => {
        console.log(value);
      });
    },
    createNewTeam() {
      this.$prompt("请输入队伍名称（3到15字）", "创建新队伍", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[\u4E00-\u9FA5A-Za-z0-9_]{3,15}$/,
        inputErrorMessage: "格式不正确",
      }).then(({ value }) => {
        console.log(value);
      });
    },
    addByInviteCode() {},
  },
};
</script>

<style>
.teamItem {
  margin: 0 20px 20px 0;
  display: inline-block;
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

#addButtonBox {
  margin: 20px auto;
}

#addButtonBox button {
  display: block;
  width: 180px;
  margin: 0 auto;
  margin-bottom: 18px;
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