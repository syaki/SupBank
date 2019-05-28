<template>
  <div id="app">
    <router-view/>
    <AppFooter/>
  </div>
</template>

<script>
import AppFooter from '@/components/AppFooter.vue';

export default {
  name: 'app',
  components: {
    AppFooter,
  },
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
  color: #1a1a1a;
  background: #f6f6f6;
}

a {
  text-decoration: none;
  cursor: pointer;
  color: inherit;
}

button {
  cursor: pointer;
  outline: none;
}

input {
  outline: none;
}

.card {
  width: auto;
  background: #fff;
  overflow: hidden;
  border-radius: 2px;
  -webkit-box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  padding: 36px;
  margin-bottom: 14px;
}

.icon {
  vertical-align: text-bottom;
  text-align: center;
}

.dividerCol {
  display: inline-block;
  width: 1px;
  margin: 0 8px;
}

.dividerRow {
  width: 100%;
  height: 1px;
  background-color: rgb(204, 204, 204);
  margin: 10px 0px;
}
</style>
