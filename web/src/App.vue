<template>
  <div id="app">
    <AppHeader />
    <el-main>
      <div class="main">
        <router-view />
      </div>
    </el-main>
    <AppFooter />
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";
import AppFooter from "@/components/AppFooter.vue";

export default {
  name: "app",
  components: {
    AppHeader,
    AppFooter
  }
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, PingFang SC,
    Microsoft YaHei, Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei,
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-size: 15px;
  color: #606266;
  background: #f6f6f6;
}

.main {
  position: relative;
  width: 1000px;
  padding: 0 16px;
  margin: 0 auto;
}

.card {
  width: auto;
  background: #fff;
  overflow: hidden;
  border-radius: 2px;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  box-sizing: border-box;
  padding: 36px;
  margin-bottom: 14px;
}

.title {
  text-align: center;
  font-size: 32px;
  color: #409eff;
  margin: 32px;
}

.title-2 {
  margin: 26px;
  font-size: 24px;
  color: #606266;
}
</style>
