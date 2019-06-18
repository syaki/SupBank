import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
      meta: {
        title: "SupBank"
      }
    },
    {
      path: "/signup",
      name: "signup",
      component: () => import("./views/Signup.vue"),
      meta: {
        title: "Sign Up"
      }
    },
    {
      path: "/signin",
      name: "signin",
      component: () => import("./views/Signin.vue"),
      meta: {
        title: "Sign In"
      }
    },
    {
      path: "/search",
      name: "search",
      component: () => import("./views/Search.vue"),
      meta: {
        title: "Search"
      }
    },
    {
      path: "/transaction",
      name: "transaction",
      component: () => import("./views/Transaction.vue"),
      meta: {
        title: "Transaction"
      }
    },
    {
      path: "/block",
      name: "block",
      component: () => import("./views/Block.vue"),
      meta: {
        title: "Block"
      }
    }
  ]
});
