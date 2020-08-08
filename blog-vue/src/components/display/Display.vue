<template>
  <el-container class="container">
    <el-header>
      <el-menu router :default-active="this.$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item :index="'/'" class="menu-item">首页</el-menu-item>
        <el-menu-item :index="'/archive'">归档</el-menu-item>
        <el-menu-item :index="'/displayCategory'">分类</el-menu-item>
        <el-menu-item :index="'/displayTag'">标签</el-menu-item>
        <el-menu-item :index="'/about'">关于</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view v-if="this.$route.name == '博客首页'"></router-view>
      <el-row :gutter="20" v-else>
        <el-col :span="16" :offset="4">
          <el-card class="box-card">
            <router-view></router-view>
          </el-card>
        </el-col>
      </el-row>
      <el-footer>
        <div class="footer">© 2020 DKT</div>
      </el-footer>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Display",
    data() {
      return {
        activeIndex: '1',
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        // console.log(key, keyPath);
      }
    },
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
  &:last-child {
     margin-bottom: 0;
   }
  }
  .el-col {
    border-radius: 4px;
  }

  .container {
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
  }

  .menu-item {
    margin-left: 250px;
  }

  .footer {
    line-height: 60px;
    font-size: 14px;
    color: #999;
  }

  .box-card {
    width: 100%;
    text-align: left;
  }
</style>
