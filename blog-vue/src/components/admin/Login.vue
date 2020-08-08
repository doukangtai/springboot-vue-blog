<template>
  <el-form :model="loginForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="loginForm"
           v-loading="loading">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="loginForm.username" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-checkbox v-model="checked" class="checkbox">记住密码</el-checkbox>
    <el-form-item>
      <el-button type="primary" @click="submitForm">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        loginForm: {
          username: 'admin',
          password: '123',
        },
        checked: true,
        loading: false,
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
        }
      };
    },
    methods: {
      submitForm() {
        const _this = this;
        this.loading = true;
        this.postRequest('/lg', {
          username: _this.loginForm.username,
          password: _this.loginForm.password,
        }).then(value => {
          _this.loading = false;
          if (value && value.status == 200) {
            if (value.data.status == 'success') {
              let data = value.data.msg;
              _this.$store.commit('login', data);
              _this.$router.push({path: '/writeArticle'});
            } else {
              _this.$router.push({path: '/login'});
            }
          }
        })
      },
    }
  }
</script>

<style scoped>
  .loginForm {
    margin: 200px auto 100px;
    width: 400px;
    padding: 80px 100px 30px 0px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px #dedede;
  }

  .checkbox {
    margin: 0px 0px 10px 100px;
  }
</style>
