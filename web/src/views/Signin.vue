<template>
  <div id="signin" class="card">
    <p class="title">Sign In</p>
    <el-form
      :label-position="labelPosition"
      :rules="rules"
      label-width="80px"
      ref="signup_form"
      :model="signin_form"
    >
      <el-form-item label="Username" prop="username">
        <el-input v-model="signin_form.username"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="signin_form.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="signin-btn" @click="sign_in"
          >Sign In</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "signin",
  data() {
    return {
      labelPosition: "top",
      signin_form: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          { required: true, message: `不能为空` },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符" }
        ],
        password: [
          { required: true, message: `不能为空` },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符" }
        ]
      }
    };
  },
  methods: {
    sign_in: function() {
      this.$axios({
        method: "post",
        url: "/user/login",
        data: {
          username: this.signin_form.username,
          password: this.signin_form.password
        }
      })
        .then(response => {
          if (response.data.status.Ack === "success") {
            this.$message({
              message: "登陆成功",
              type: "success"
            });
            sessionStorage.setItem("username", this.signin_form.username);
            this.$router.push({
              path: "/"
            });
            this.$router.go(0);
          } else {
            this.$message.error(response.data.status.ErrorMessage);
          }
        })
        .catch(() => {
          this.$message.error("请求发送失败");
        });
    }
  }
};
</script>

<style scoped>
#signin {
  position: relative;
  width: 560px;
  margin: 0 auto;
}

.signin-btn {
  margin-left: 360px;
}
</style>
