<template>
  <div id="asideBody">
    <ul>
      <li>评论</li>
      <li>点赞</li>
      <li @click="doRoute('/notify/team')">组队信息</li>
      <li @click="doRoute('/notify/system')">系统通知</li>
    </ul>
  </div>
</template>

<script>
export default {
  methods: {
    doRoute(path) {
      this.$doRoute(path);
    }
  }
};
</script>

<style>
#asideBody ul {
  background: #fff;
  padding: 8px 0;
  border-radius: 2px;
  list-style: none;
}

#asideBody ul li {
  background: white;
  height: 48px;
  line-height: 48px;
  font-size: 14px;
  position: relative;
  text-align: center;
}

#asideBody ul li:hover {
  background: #fafafa;
  cursor: pointer;
  color: rgb(14, 218, 218);
}
</style>