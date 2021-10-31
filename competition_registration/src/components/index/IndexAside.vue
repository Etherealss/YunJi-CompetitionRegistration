<template>
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
</template>

<script>
export default {
  methods: {
    doRoute(path) {
      this.$doRoute(path);
    },
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