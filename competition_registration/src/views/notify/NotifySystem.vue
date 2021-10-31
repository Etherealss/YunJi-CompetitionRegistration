<template>
  <div>
    <div class="notifySystemTitle">
      {{ systemMessage.title }}
    </div>
    <div
      class="notifySystemBody"
      v-for="(item, index) in systemMessage.items"
      :key="index"
    >
      <div v-if="item.sender != undifined">
        <div class="notifySystemUserAvatar">
          <el-avatar size="medium" :src="item.sender.avatar"></el-avatar>
        </div>
      </div>
      <div v-else></div>
      <div v-if="item.sender != undifined" class="notifySystemDetails">
        <!-- 带发送者的系统消息 -->
        <!-- 发送信息 -->
        <!-- 发送者用户名 -->
        <div class="notifySystemUsername">{{ item.sender.username }}</div>
        <!-- 时间 -->
        <div class="notifySystemTime">
          {{ dayjs(item.time).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <div class="notifySystemMessage">
          {{ item.message }}
        </div>
      </div>
      <div v-else class="notifySystemDetails">
        <!-- 无发送者的系统消息 -->
        <!-- 发送信息 -->
        <!-- 发送者用户名 -->
        <div class="notifySystemSystemTitle"><span>系统通知</span></div>
        <!-- 时间 -->
        <div class="notifySystemTime">
          {{ dayjs(item.time).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <div class="notifySystemMessage">
          {{ item.message }}
        </div>
      </div>
      <!-- 按钮 -->
      <div class="notifySystemActions">
        <el-button size="small" plain>删除</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      systemMessage: {
        title: "系统信息",
        items: [
          {
            title: "报名成功",
            message: "你的队伍 你说的都对 报名比赛 软件设计大赛 成功！",
            type: "EnrollmentSuccessful",
            createTime: new Date(),
            sender: {
              id: "12312",
              username: "Pythoncb",
              avatar: "",
            },
          },
          {
            title: "报名成功",
            message: "你的队伍 你说的都对2 报名比赛 软件设计大赛2 成功！",
            type: "EnrollmentSuccessful",
            createTime: new Date(),
          },
        ],
      },
    };
  },
};
</script>

<style>
.notifySystemTitle {
  font-size: 16px;
  font-weight: 600;
  color: #222226;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f5;
  line-height: 22px;
}

.notifySystemBody {
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

.notifySystemDetails {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  padding-left: 16px;
  overflow: hidden;
}

.notifySystemUsername {
  margin-bottom: 12px;
  font-weight: 500;
  font-size: 15px;
}

.notifySystemTime {
  display: inline-block;
  margin: 0 20px 0 0;
  font-size: 10px;
}

.notifySystemMessage {
  display: inline-block;
  height: 22px;
  line-height: 22px;
}
.notifySystemSystemTitle span {
  font-weight: 600;
}
</style>