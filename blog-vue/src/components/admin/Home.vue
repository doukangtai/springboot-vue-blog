<template>
  <el-container class="container" v-loading="loading">
    <el-header class="header">
      <span class="title">简单博客</span>
      <el-dropdown @command="handleCommand" class="dropdown">
  <span class="el-dropdown-link dropdown-link">
    {{this.$store.state.user.nickname}}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container>
      <el-aside width="250px" class="aside">
        <el-row class="tac">
          <el-col>
            <el-menu
                router
                :default-active="$route.path"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                background-color="#53567a"
                text-color="#fff"
                active-text-color="#ffd04b">
              <el-submenu :index="'1'" :key="1">
                <template slot="title">
                  <i class="el-icon-document"></i>
                  <span>文章管理</span>
                </template>
                <el-menu-item :index="'/writeArticle'" :key="'writeArticle'">写文章</el-menu-item>
                <el-menu-item :index="'/articleList'" :key="'articleList'">文章列表</el-menu-item>
              </el-submenu>
              <el-submenu :index="'2'" :key="2">
                <template slot="title">
                  <i class="el-icon-setting"></i>
                  <span>系统设置</span>
                </template>
                <el-menu-item :index="'/updateInfo'">更新信息</el-menu-item>
              </el-submenu>
            </el-menu>
          </el-col>
        </el-row>
      </el-aside>
      <el-main>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item v-if="this.$route.name != 'Home'">{{this.$route.name}}</el-breadcrumb-item>
        </el-breadcrumb>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "Home",
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleCommand(command) {
        const _this = this;
        if (command == 'logout') {
          _this.loading = true;
          this.getRequest('/logout').then(value => {
            _this.loading = false;
            if (value && value.status == 200 && value.data.status == 'success') {
              _this.$store.commit('logout');
              _this.$router.push({path: '/login'});
            } else {
              _this.$message.error({message: '登出失败'});
            }
          })
        }
      },
    },
    data() {
      return {
        loading: false,
      }
    }
  }
</script>

<style scoped>
  .container {
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
  }

  .header {
    background-color: #43ae9e;
  }

  .aside {
    background-color: #53567a;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }

  .title {
    float: left;
    margin-left: 10px;
    line-height: 60px;
    font-size: large;
  }

  .dropdown {
    float: right;
    line-height: 60px;
  }

  .dropdown-link {
    color: #ff9799;
  }
</style>
