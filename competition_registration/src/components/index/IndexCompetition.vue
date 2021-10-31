<template>
  <el-card class="compBox" shadow="hover">
    <h3>{{ comp.name }}</h3>
    <p>{{ comp.profile }}</p>
    <div>
      <!-- 发布组织 -->
      <div class="compOrganization">
        <span>{{ comp.organizationName }}</span>
      </div>
      <!-- 修改时间 -->
      <div class="compUpdateTime">
        <span>{{
          this.dayjs(comp.updateTime).format("YYYY-MM-DD HH:mm:ss")
        }}</span>
      </div>
      <!-- 点赞 -->
      <div class="compLike">
        <img class="likeImg" src="@/assets/icon/like.png" alt="点赞" />
        <span>61赞</span>
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  props: {
    comp: {
      name: { type: String, require: true },
      profile: { type: String, require: true },
      organizationName: { type: String, require: true },
      updateTime: { type: Date, require: true },
    },
  },
};
</script>

<style>
.el-card__body {
  padding: 0;
}
.compBox {
  /* border: 1px solid #f00; */
  width: 859px;
  margin: 20px 0;
  padding: 8px 20px 10px 20px;
}
/* 
.compBox:hover {
  background-color: rgb(247, 247, 247);
} */

.compBox h3 {
  display: inline-block;
  color: #343434;
  font-size: 19px;
  font-weight: 700;
  letter-spacing: 2px;
}

.compBox p {
  margin: 10px 10px 20px 0;
  font-size: 14px;
  font-weight: 400;
  /* 最小高度 */
  min-height: 40px;
  color: #999;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  /* 显示3行 */
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  word-break: break-all;
}

.compOrganization {
  margin: 0 20px 0 0;
  display: inline-block;
}

.compUpdateTime {
  display: inline-block;
}
.compLike {
  margin: 0 27px 0 0;
  float: right;
}
.likeImg {
  width: 20px;
  height: 20px;
  margin: 0 7px 5px 0;
}
</style>