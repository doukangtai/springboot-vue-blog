<template>
  <el-row :gutter="20">
    <el-col :span="16" :offset="4">
      <el-card class="box-card" v-for="(item, index) in article">
        <h1 class="title" @click="getArticleDetail(item.id)">{{item.title}}</h1>
        <div class="tct">
          <i class="el-icon-time marginrl"></i>发表于
          <span>{{item.time}}</span>
          <span class="marginrl">|</span>
          <i class="el-icon-folder-opened marginrl"></i>
          <span class="category" @click="getArticleByCategory(item.category.id, item.category.category)">{{item.category.category}}</span>
          <span class="marginrl">|</span>
          <span v-for="(it, ind) in item.tags">
          <i class="el-icon-collection-tag marginrl"></i>
          <span class="tag" @click="getArticleByTagId(it.id, it.tag)">{{it.tag}}</span>
          </span>
        </div>
        <div class="content">
          {{item.content}}
        </div>
        <div class="more" @click="getArticleDetail(item.id)">
          <span>阅读全文</span>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "DisplayArticle",
    mounted() {
      const _this = this;
      _this.getAllArticleSubstringContent();
    },
    methods: {
      getAllArticleSubstringContent() {
        const _this = this;
        _this.getRequest('/getAllArticleSubstringContent').then(value => {
          if (value.status == 200) {
            _this.article = value.data;
          }
        })
      },
      getArticleDetail(id) {
        this.$router.push({path: '/articleDetail', query: {articleId: id}});
      },
      getArticleByCategory(id, category) {
        this.$router.push({path: '/archive', query: {categoryId: id, categoryName: category}});
      },
      getArticleByTagId(id, tag) {
        this.$router.push({path: '/archive', query: {tagId: id, tagName: tag}});
      },
    },
    data() {
      return {
        article: [],
      }
    }
  }
</script>

<style scoped>
  .box-card {
    width: 100%;
    margin-bottom: 20px;
  }

  .el-row {
    margin-bottom: 20px;

  &
  :last-child {
    margin-bottom: 0;
  }

  }
  .el-col {
    border-radius: 4px;
  }

  a {
    display: inline-block;
    text-decoration: none;
  }

  .title {
    margin-top: 0;
    font-weight: 400;
    color: black;
    cursor: pointer;
  }

  .title:hover {
    color: #a393eb;
  }

  .tct {
    margin: 0 0 30px 0;
    color: #999999;
    font-size: 13px;
  }

  .category, .tag {
    cursor: pointer;
  }

  .category:hover, .tag:hover {
    text-decoration: underline;
  }

  .marginrl {
    margin: 0 3px;
  }

  .content {
    line-height: 25px;
  }

  .more {
    margin: 20px auto;
  }

  .more span {
    color: black;
    border: 3px solid black;
    padding: 5px 20px;
    cursor: pointer;
  }

  .more span:hover {
    background-color: black;
    color: white;
  }
</style>
