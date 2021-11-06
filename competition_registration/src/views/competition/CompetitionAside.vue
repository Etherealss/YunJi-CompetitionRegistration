<template>
  <div>
    <div id="compAsideBody">
      <ul>
        <li @click="toRegister()">
          <div><img src="@/assets/icon/competition_sign_up.png" /></div>
          <span>报名</span>
        </li>
        <li>
          <div><img src="@/assets/icon/competition_team.png" /></div>
          <span>组队</span>
        </li>
        <li>
          <div><img src="@/assets/icon/competition_attention.png" /></div>
          <span>关注</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    doRoute(path) {
      this.$doRoute(path);
    },
    toRegister() {
      // 调用父组件的方法，去报名
      this.$parent.toRegister();
    },
  },
};
</script>

<style>
#compAsideBody ul {
  background: #fff;
  padding: 8px 0;
  border-radius: 2px;
  list-style: none;
}

#compAsideBody ul li {
  background: white;
  height: 48px;
  line-height: 48px;
  font-size: 14px;
  position: relative;
  text-align: center;
  letter-spacing: 15px;
  text-indent: 15px;
  font-size: 16px;
  color: #444;
}

#compAsideBody ul li:hover {
  cursor: pointer;
  color: rgb(14, 218, 218);
}

#compAsideBody ul li div {
  display: inline-block;
  border-radius: 10px;
  width: 40px;
  height: 40px;
  margin: 0 20px 0 -20px;
}

#indexAsideBox ul li div img {
  /* 居中 */
  margin: 7px;
  width: 25px;
  height: 25px;
}
</style>