<template>
  <div>
    <div id="unlogged" v-show="true">
      <el-button type="primary" size="small" @click="toSignIn">登录</el-button>
      <el-button size="small" @click="toSignUp">注册</el-button>
    </div>
    <div id="logged" v-show="false">
      <el-avatar :size="60" src="https://empty" @error="errorHandler">
        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
      </el-avatar>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    toSignIn() {
      this.$router.push('/login')
    },
    toSignUp() {
      this.$router.push('/register')
    },
    // 图片获取错误的判断逻辑
    errorHandler() {
      return true;
    },
  },
};
</script>

<style>
#unlogged {
    margin: 10px 50px 0 0;
}
</style>