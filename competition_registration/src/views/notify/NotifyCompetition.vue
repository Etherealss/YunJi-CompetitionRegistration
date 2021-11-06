<template>
  <div>
    <div class="notifyCompetitionTitle">赛事信息</div>
    <div
      class="notifyCompetitionBody"
      v-for="(item, index) in notifications"
      :key="index"
    >
      <div class="notifyCompetitionUserAvatar">
        <el-avatar
          size="medium"
          :src="'/api/users/public/avatar' + item.sender.avatar"
        ></el-avatar>
      </div>
      <div class="notifyCompetitionDetails">
        <div class="notifyCompetitionUsername">{{ item.sender.username }}</div>
        <div class="notifyCompetitionTime">
          {{ dayjs(item.createTime).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <div
          class="notifyCompetitionMessage"
          v-html="getNotifyMessage(item.message, item.elements, item.type)"
        ></div>
      </div>
      <div class="notifyCompetitionActions" v-if="isOfficial">
        <el-button size="small" plain>删除</el-button>
      </div>
      <div class="notifyCompetitionActions" v-else>
        <el-button type="success" size="small" @click="respAddTeam(item, true)"
          >同意</el-button
        >
        <el-button size="small" plain @click="respAddTeam(item, false)"
          >拒绝</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import StringUtil from "@/utils/StringUtil.js";
export default {
  data() {
    return {
      notifications: [],
    };
  },
  methods: {
    isOfficial() {
      return this.$store.getters.isOfficial;
    },
    getNotifyMessage(message, elements, type) {
      if (elements.length == 0) {
        return message;
      }
      if (type == "RegisterCompetition" || type == "NewRegisterCompetition") {
        let s1 =
          "<span class='notifyCompetitionElement' @click=\"doRoute('/users/teams')\">" +
          elements[0].target.name +
          "</span>";
        let s2 =
          "<span class='notifyCompetitionElement' @click=\"doRoute('/competition/" +
          elements[0].target.id +
          "')\">" +
          elements[1].target.name +
          "</span>";
        message = StringUtil.format(message, s1, s2);
      }
      return message;
    },
    doRoute(path) {
      this.$doRoute(path);
    },
  },
  mounted() {
    this.$axios({
      method: "get",
      url: "/notifications/competitions",
    }).then((r) => {
      if (r.code == 200) {
        this.notifications = r.data;
      }
    });
  },
};
</script>

<style>
.notifyCompetitionTitle {
  font-size: 16px;
  font-weight: 600;
  color: #222226;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f5;
  line-height: 22px;
}

.notifyCompetitionBody {
  position: relative;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f2;
  display: flex;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -ms-flex-direction: row;
  flex-direction: row;
  padding: 24px 0;
}

.notifyCompetitionDetails {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  padding-left: 16px;
  overflow: hidden;
}

.notifyCompetitionUsername {
  margin-bottom: 12px;
  font-weight: 500;
  font-size: 15px;
}

.notifyCompetitionTime {
  display: inline-block;
  margin: 0 20px 0 0;
  font-size: 10px;
}

.notifyCompetitionMessage {
  display: inline-block;
  height: 22px;
  line-height: 22px;
}
.notifyCompetitionElement {
  cursor: pointer;
  color: #3399ea;
}
</style>