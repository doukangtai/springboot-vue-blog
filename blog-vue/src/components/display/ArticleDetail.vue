<template>
  <div>
    <h1 class="title">{{article.title}}</h1>
    <div class="tct">
      <i class="el-icon-time marginrl"></i>发表于
      <span>{{article.time}}</span>
      <span class="marginrl">|</span>
      <i class="el-icon-folder-opened marginrl"></i>
      <span class="category" @click="getArticleByCategory(article.category.id, article.category.category)">{{article.category.category}}</span>
      <span class="marginrl">|</span>
      <span v-for="(it, ind) in article.tags">
        <i class="el-icon-collection-tag marginrl"></i>
        <span class="tag" @click="getArticleByTagId(it.id, it.tag)">{{it.tag}}</span>
      </span>
    </div>
    <div v-html="code" class="article markdown-body"></div>
  </div>
</template>

<script>
  import marked from 'marked'
  import hljs from 'highlight.js'
  import 'highlight.js/styles/github-gist.css';
  import '../../assets/github-markdown.css'

  export default {
    name: "ArticleDetail",
    mounted() {
      const _this = this;
      _this.articleId = _this.$route.query.articleId;
      if (_this.articleId) {
        this.getArticleById(_this.articleId);
        setTimeout(_this.getHTML, 500);
      }
    },
    methods: {
      getArticleById(id) {
        const _this = this;
        _this.getRequest('/getArticleById/' + id).then(value => {
          _this.article = value.data;
        })
      },
      getHTML() {
        const _this = this;
        marked.setOptions({
          renderer: new marked.Renderer(),
          highlight: function (code) {
            return hljs.highlightAuto(code).value;
          },
          pedantic: false,
          gfm: true,
          tables: true,
          breaks: false,
          sanitize: false,
          smartLists: true,
          smartypants: false,
          xhtml: false
        });
        this.code = marked(_this.article.content);
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
        code: '',
        article: '',
        articleId: '',
      }
    }
  }
</script>

<style scoped>
  .article {
    text-align: left;
  }

  .markdown-body {
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    padding: 45px;
  }

  @media (max-width: 767px) {
    .markdown-body {
      padding: 15px;
    }
  }

  .title {
    margin-bottom: 0;
    text-align: center;
  }

  .tct {
    margin-top: 10px;
    color: #999999;
    font-size: 13px;
    text-align: center;
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
</style>
