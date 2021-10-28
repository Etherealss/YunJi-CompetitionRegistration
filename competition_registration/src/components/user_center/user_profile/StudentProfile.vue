<template>
  <el-card shadow="always" class="profileGroup">
    <div>学生姓名:{{ student.name }}</div>
    <div>学生性别：{{ student.sex }}</div>
    <div>生日：{{ this.dayjs(student.birthday).format("YYYY-MM-DD") }}</div>
    <div>学校名称：{{ student.schoolName }}</div>
    <div>专业信息：{{ student.major }}</div>
    <div>
      入学年份：{{ this.dayjs(student.enrollmentDate).format("YYYY年") }}
    </div>
  </el-card>
</template>

<script>
export default {
  props: ["student"],
};
</script>

<style>
</style>