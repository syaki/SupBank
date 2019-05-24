import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        title: 'SupBank',
      },
    },
    {
      path: '/signin',
      name: 'signin',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Signin.vue'),
      meta: {
        title: 'Sign In',
      },
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('./views/Signup.vue'),
      meta: {
        title: 'Sign Up',
      },
    },
    {
      path: '/transaction',
      name: 'transaction',
      component: () => import('./views/Transaction.vue'),
      meta: {
        title: 'Transaction',
      },
    },
    {
      path: '/search',
      name: 'search',
      component: () => import('./views/Search.vue'),
      meta: {
        title: 'Search',
      },
    },
    {
      path: '/block',
      name: 'block',
      component: () => import('./views/Block.vue'),
      meta: {
        title: 'Block',
      },
    },
  ],
});
