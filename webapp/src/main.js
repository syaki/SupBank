import Vue from 'vue';
import axios from 'axios';
import fnv from 'fnv-plus';
import App from './App.vue';
import router from './router';

Vue.prototype.$axios = axios;
Vue.prototype.$fnv = fnv;

Vue.config.productionTip = false;

router.afterEach((to) => {
  document.title = to.meta.title || 'SupBank';
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
