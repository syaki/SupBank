<template>
  <div id="signup" class="card">
    <p class="title">Sign Up</p>
    <el-form
      :label-position="labelPosition"
      :rules="rules"
      label-width="80px"
      ref="signup_form"
      :model="signup_form"
    >
      <el-form-item label="Username" prop="username">
        <el-input v-model="signup_form.username"></el-input>
      </el-form-item>
      <el-form-item label="Email Address" prop="emailaddress">
        <el-input v-model="signup_form.emailaddress"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="signup_form.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="Verification Code" prop="vcode">
        <el-input v-model="signup_form.vcode"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="sendcode-btn" @click="send_code"
          >Send Code</el-button
        >
        <el-button type="primary" class="signup-btn" @click="sign_up"
          >Sign Up</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "signup",
  data() {
    return {
      labelPosition: "top",
      signup_form: {
        username: "",
        emailaddress: "",
        password: "",
        vcode: ""
      },
      rules: {
        username: [
          { required: true, message: `不能为空` },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符" }
        ],
        emailaddress: [
          { required: true, message: `不能为空` },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        password: [
          { required: true, message: `不能为空` },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符" }
        ],
        vcode: [
          { required: true, message: `不能为空` },
          { min: 6, max: 6, message: "长度为 6 个字符" }
        ]
      }
    };
  },
  methods: {
    send_code: function() {
      this.$axios({
        method: "post",
        url: "/user/sendCode",
        data: {
          email: this.signup_form.emailaddress
        }
      })
        .then(response => {
          if (response.data.status.Ack === "success") {
            this.$message({
              message: "验证码发送成功",
              type: "success"
            });
          } else {
            this.$message.error(response.data.status.ErrorMessage);
          }
        })
        .catch(() => {
          this.$message.error("验证码发送失败");
        });
    },
    sign_up: function() {
      this.$axios({
        method: "post",
        url: "/user/register",
        data: {
          email: this.signup_form.emailaddress,
          username: this.signup_form.username,
          password: this.signup_form.password,
          verificationCode: this.signup_form.vcode
        }
      })
        .then(response => {
          if (response.data.status.Ack === "success") {
            this.$message({
              message: "注册成功",
              type: "success"
            });
            this.$router.push({
              path: "/signin"
            });
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
#signup {
  position: relative;
  width: 560px;
  margin: 0 auto;
}

.sendcode-btn {
  margin-left: 40px;
  margin-right: 50px;
}

.signup-btn {
  margin-left: 145px;
}
</style>
