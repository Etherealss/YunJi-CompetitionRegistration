<template>
  <div>
    <div class="notifyTeamTitle">
      {{ teamMessage.title }}
    </div>
    <div
      class="notifyTeamBody"
      v-for="(item, index) in teamMessage.items"
      :key="index"
    >
      <div class="notifyUserAvatar">
        <el-avatar size="medium" :src="item.user.avatar"></el-avatar>
      </div>
      <div class="notifyDetails">
        <div class="notifyUsername">{{ item.user.username }}</div>
        <div class="notifyTime">
          {{ dayjs(item.notify.time).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <div class="notifyMessage" v-if="(item.notify.type = 'requestAddTeam')">
          请求加入你的队伍：<span class="notifyTeamTarget">{{
            item.target.name
          }}</span>
        </div>
        <div class="notifyMessage" v-else-if="(item.notify.type = 'leaveTeam')">
          离开了你的队伍：<span class="notifyTeamTarget">{{
            item.target.name
          }}</span>
        </div>
      </div>
      <div class="notifyActions">
        <el-button type="success" size="small">同意</el-button>
        <el-button size="small" plain>拒绝</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      teamMessage: {
        title: "组队信息",
        items: [
          {
            user: {
              id: "12312",
              username: "Pythoncb",
              avatar: "",
            },
            target: {
              id: "3",
              name: "你说的都对3",
            },
            notify: {
              scope: "team",
              type: "RequestAddTeam",
              message: "想加入你的队伍",
              time: new Date(),
            },
          },
          {
            user: {
              id: "123123112",
              username: "张飒",
              avatar: "",
            },
            target: {
              id: "2",
              name: "你说的都对2",
            },
            notify: {
              scope: "team",
              type: "LeaveTeam",
              message: "想加入你的队伍",
              time: new Date(),
            },
          },
        ],
      },
    };
  },
};
</script>

<style>
.notifyTeamTitle {
  font-size: 16px;
  font-weight: 600;
  color: #222226;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f5;
  line-height: 22px;
}

.notifyTeamBody {
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

.notifyDetails {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  padding-left: 16px;
  overflow: hidden;
}

.notifyUsername {
  margin-bottom: 12px;
  font-weight: 500;
  font-size: 15px;
}

.notifyTime {
  display: inline-block;
  margin: 0 20px 0 0;
  font-size: 10px;
}

.notifyMessage {
  display: inline-block;
  height: 22px;
  line-height: 22px;
}
</style>