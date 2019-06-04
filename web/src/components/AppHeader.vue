<template>
  <div id="app-header">
    <el-header height="54px">
      <el-row>
        <el-col :span="6" :offset="4">
          <el-button
            type="primary"
            class="gohome-btn"
            v-on:click="go_home"
            icon="el-icon-s-home"
            >SUPBANK</el-button
          >
        </el-col>
        <el-link type="primary" href="/bitcoin.apk" target="_blank"
          >下载APP</el-link
        >
        <el-col :span="6" :offset="6">
          <section class="profile-header isSignin" v-if="username">
            <el-button
              icon="el-icon-user-solid"
              type="primary"
              class="username-brand"
              plain
              >{{ username }}</el-button
            >
            <el-button
              type="primary"
              class="signout-btn"
              v-on:click="sign_out"
              plain
              >Sign Out</el-button
            >
          </section>
        </el-col>
        <el-col :span="6" :offset="6">
          <section class="profile-header noSignin" v-if="!username">
            <el-button
              type="primary"
              class="signin-btn"
              v-on:click="sign_in"
              plain
              >Sign In</el-button
            >
            <el-button
              type="primary"
              class="signup-btn"
              v-on:click="sign_up"
              plain
              >Sign Up</el-button
            >
          </section>
        </el-col>
      </el-row>
    </el-header>
  </div>
</template>

<script>
export default {
  name: "AppHeader",
  data() {
    return {
      username: ""
    };
  },
  created() {
    if (sessionStorage.getItem("username")) {
      this.username = sessionStorage.getItem("username");
    } else {
      this.username = "";
    }
  },
  methods: {
    go_home: function() {
      this.$router.push({
        path: "/"
      });
    },
    sign_in: function() {
      this.$router.push({
        path: "/signin"
      });
    },
    sign_up: function() {
      this.$router.push({
        path: "/signup"
      });
    },
    sign_out: function() {
      this.$axios({
        method: "post",
        url: "/user/logout"
      })
        .then(response => {
          if (response.data.status.Ack === "success") {
            sessionStorage.removeItem("username");
            this.$message({
              message: "退出成功",
              type: "success"
            });
            this.$router.go(0);
          } else {
            this.$message.error(response.data.status.ErrorMessage);
          }
        })
        .catch(err => {
          this.$message.error(err);
        });
    }
  }
};
</script>

<style scoped>
#app-header {
  width: 100%;
  padding-top: 10px;
  background: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
}

.profile-header {
  display: inline-block;
}
</style>
