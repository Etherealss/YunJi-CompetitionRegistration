<template>
  <div>
    <div class="notifyOrgTitle">组织信息</div>
    <el-empty
      description="暂无组织信息"
      v-show="notifications.length == 0"
    ></el-empty>
    <div
      class="notifyOrgBody"
      v-for="(item, index) in notifications"
      :key="index"
    >
      <div class="notifyUserAvatar">
        <el-avatar
          size="medium"
          :src="'/api/users/public/avatar' + item.sender.avatar"
        ></el-avatar>
      </div>
      <div class="notifyDetails">
        <div class="notifyUsername">{{ item.sender.username }}</div>
        <div class="notifyTime">
          {{ dayjs(item.createTime).format("YYYY-MM-DD HH:mm:ss") }}
        </div>
        <div
          class="notifyMessage"
          v-html="getNotifyMessage(item.message, item.elements, item.type)"
        >
          <!-- {{ getNotifyMessage(item.message, item.elements, item.type) }} -->
        </div>
      </div>
      <div
        class="notifyActions"
        v-if="item.hasRead || item.type == 'ResponseAddOrg'"
      >
        <el-button size="small" plain>删除</el-button>
      </div>
      <div class="notifyActions" v-else-if="item.type == 'RequestAddOrg'">
        <el-button type="success" size="small" @click="respAddOrg(item, true)"
          >同意</el-button
        >
        <el-button size="small" plain @click="respAddOrg(item, false)"
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
    getNotifyMessage(message, elements, type) {
      if (elements.length == 0) {
        return message;
      }
      if (
        type == "RequestAddOrg" ||
        type == "LeaveOrg" ||
        type == "ResponseAddOrg"
      ) {
        let s1 =
          "<span class='notifyElement' @click=\"doRoute('/users/profile/" +
          elements[0].target.id +
          "')\">" +
          elements[0].target.username +
          "</span>";
        let s2 =
          "<span class='notifyElement' @click=\"doRoute('/users/teams')\">" +
          elements[1].target.name +
          "</span>";
        message = StringUtil.format(message, s1, s2);
      }
      return message;
    },
    doRoute(path) {
      this.$doRoute(path);
    },
    respAddOrg(item, action) {
      this.$axios({
        method: "post",
        url: "/organizations/respAddOrg",
        data: {
          notificationId: item.id,
          action,
        },
      }).then((r) => {
        if (r.code == 200) {
          this.$notify.success({
            title: "成功",
            message: "已同意对方加入组织！",
          });
          item.hasRead = true;
        } else if (r.code == 10501) {
          this.$notify.error({
            title: "错误",
            message: "用户已经在组织中",
          });
        }
      });
    },
  },
  mounted() {
    this.$axios({
      method: "get",
      url: "/notifications/organizations",
    }).then((r) => {
      if (r.code == 200) {
        this.notifications = r.data;
      }
    });
  },
};
</script>

<style>
.notifyOrgTitle {
  font-size: 16px;
  font-weight: 600;
  color: #222226;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f5;
  line-height: 22px;
}

.notifyOrgBody {
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
.notifyElement {
  cursor: pointer;
  color: #3399ea;
}
</style>