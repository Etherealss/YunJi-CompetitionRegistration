<template>
  <div>
    <div class="notifySystemTitle">系统信息</div>
    <el-empty
      description="暂无系统消息"
      v-show="systemMessages.length == 0"
    ></el-empty>
    <div
      class="notifySystemBody"
      v-for="(item, index) in systemMessages"
      :key="index"
    >
      <div v-if="item.sender != 'undifined'">
        <div class="notifySystemUserAvatar">
          <el-avatar
            size="medium"
            :src="'/api/users/public/avatar' + item.sender.avatar"
          ></el-avatar>
        </div>
      </div>
      <div v-else></div>
      <div v-if="item.sender != 'undifined'" class="notifySystemDetails">
        <!-- 带发送者的系统消息 -->
        <!-- 发送者用户名 -->
        <div class="notifySystemUsername">{{ item.sender.username }}</div>
        <!-- 时间 -->
        <div class="notifySystemTime">
          {{ dayjs(item.createTime).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <!-- 信息 -->
        <div class="notifySystemMessage">
          <!-- 拼接字符串 -->
          {{ item.message.substring(0, item.message.indexOf("{}")) }}
          <span
            class="notifyElement"
            @click="doRoute('/competitions/' + item.elements[0].target.id)"
          >
            {{ item.elements[0].target.name }}
          </span>
          {{ item.message.substring(item.message.indexOf("{}") + 2) }}
        </div>
        <!-- <div 
          class="notifySystemMessage"
          v-html="getNotifyMessage(item.message, item.elements, item.type)"
        >
        <span class='notifyElement' @click="doRoute('/competitions/${elements[0].target.id}')">
          ${elements[0].target.name} 
          </span></div> -->
      </div>
      <div v-else class="notifySystemDetails">
        <!-- 无发送者的系统消息 -->
        <!-- 发送者用户名 -->
        <div class="notifySystemSystemTitle"><span>系统通知</span></div>
        <!-- 时间 -->
        <div class="notifySystemTime">
          {{ dayjs(item.createTime).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <div
          class="notifySystemMessage"
          v-html="getNotifyMessage(item.message, item.elements, item.type)"
        ></div>
      </div>
      <!-- 按钮 -->
      <div class="notifySystemActions">
        <el-button size="small" plain>删除</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import StringUtil from "@/utils/StringUtil.js";
export default {
  data() {
    return {
      systemMessages: [],
    };
  },
  methods: {
    getNotifyMessage(message, elements, type) {
      if (elements.length == 0) {
        return message;
      }
      if (type == "ReviewCompetition") {
        let s1 = `<span class='notifyElement' @click="doRoute('/competitions/${elements[0].target.id}')">
          ${elements[0].target.name} 
          </span>`;
        message = StringUtil.format(message, s1);
      }
      console.log(message);
      return message;
    },
    doRoute(path) {
      this.$doRoute(path);
    },
  },
  beforeMount() {
    this.$axios.get("/notifications/systems").then((r) => {
      if (r.code == 200) {
        this.systemMessages = r.data;
      }
    });
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