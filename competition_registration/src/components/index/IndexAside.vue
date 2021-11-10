<template>
  <div>
    <div v-if="userRole == 'student'">
      <div id="indexAsideBox">
        <ul>
          <li @click="doRoute('/users/entered')">
            <div>
              <img src="@/assets/icon/my_competitions.png" />
            </div>
            <span>我的比赛</span>
          </li>
          <li>
            <div>
              <img src="@/assets/icon/my_draft.png" />
            </div>
            <span>草稿箱</span>
          </li>
          <li @click="doRoute('/users/watched')">
            <div>
              <img src="@/assets/icon/my_attention.png" />
            </div>
            <span>我的关注</span>
          </li>
          <li @click="doRoute('/users/teams')">
            <div>
              <img src="@/assets/icon/my_group.png" />
            </div>
            <span>我的队伍</span>
          </li>
        </ul>
      </div>
    </div>
    <!-- 官方人员 -->
    <div v-else-if="userRole == 'official'">
      <div id="indexAsideBox">
        <ul>
          <li @click="doRoute('/create/competitions')">
            <div>
              <img src="@/assets/icon/publish.png" />
            </div>
            <span>发布新赛事</span>
          </li>
          <li>
            <div>
              <img src="@/assets/icon/my_draft.png" />
            </div>
            <span>草稿箱</span>
          </li>
          <li>
            <div>
              <img src="@/assets/icon/my_published.png" />
            </div>
            <span>我的发布</span>
          </li>
          <li @click="doRoute('/notify/competition')">
            <div>
              <img src="@/assets/icon/competition_message.png" />
            </div>
            <span>赛事信息</span>
          </li>
        </ul>
      </div>
    </div>
    <!-- 管理员 -->
    <div v-else-if="userRole == 'admin'">
      <div id="indexAsideBox">
        <ul>
          <li @click="doRoute('/admins/review/competitions')">
            <div>
              <img src="@/assets/icon/review_competition.png" />
            </div>
            <span>审核比赛</span>
          </li>
          <li @click="doRoute('/admins/review/organizations')">
            <div>
              <img src="@/assets/icon/review_organization.png" />
            </div>
            <span>审核组织</span>
          </li>
          <li @click="doRoute('/admins/manage/permissions')">
            <div>
              <img src="@/assets/icon/management_permission.png" />
            </div>
            <span>权限管理</span>
          </li>
          <li @click="doRoute('/admins/manage/competitions')">
            <div>
              <img src="@/assets/icon/management_competition.png" />
            </div>
            <span>赛事管理</span>
          </li>
        </ul>
      </div>
    </div>
    <!-- 普通用户或者游客 -->
    <div v-else>
      <div id="indexAsideBox">
        <ul>
          <li @click="doRoute('/users/entered')">
            <div>
              <img src="@/assets/icon/my_competitions.png" />
            </div>
            <span>我的比赛</span>
          </li>
          <li>
            <div>
              <img src="@/assets/icon/my_draft.png" />
            </div>
            <span>草稿箱</span>
          </li>
          <li @click="doRoute('/users/watched')">
            <div>
              <img src="@/assets/icon/my_attention.png" />
            </div>
            <span>我的关注</span>
          </li>
          <li @click="doRoute('/users/teams')">
            <div>
              <img src="@/assets/icon/my_group.png" />
            </div>
            <span>我的队伍</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userRole: "",
    };
  },
  methods: {
    doRoute(path) {
      this.$doRoute(path);
    },
  },
  beforeMount() {
    let user = this.$store.getters.getUserDetails;
    this.userRole = user == undefined ? "" : user.userRole;
  },
};
</script>

<style>
#indexAsideBox {
  margin: 22px 21px 0 0;
  width: 237px;
  box-shadow: 1px 1px 10px rgba(204, 204, 204, 0.6);
  padding: 20px 0;
}
#indexAsideBox ul {
  list-style-type: none;
  margin: 10px 10px;
}

#indexAsideBox ul li {
  margin: 20px 0 0 0;
  cursor: pointer;
}

#indexAsideBox ul li span:hover {
  color: rgb(14, 218, 218);
}

#indexAsideBox ul li div {
  display: inline-block;
  margin: 0 10px 0 0;
  background: #d0edee;
  border-radius: 10px;
  width: 40px;
  height: 40px;
}

#indexAsideBox ul li div img {
  /* 居中 */
  margin: 7px;
  width: 25px;
  height: 25px;
}
</style>