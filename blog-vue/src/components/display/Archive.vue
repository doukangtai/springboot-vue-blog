<template>
  <div class="block">
    <el-timeline>
      <el-timeline-item placement="bottom" v-if="categoryName"><h4>{{categoryName}}</h4></el-timeline-item>
      <el-timeline-item placement="bottom" v-else-if="tagName"><h4>{{tagName}}</h4></el-timeline-item>
      <el-timeline-item placement="bottom" v-else><h4>时间线</h4></el-timeline-item>
      <el-timeline-item v-for="(item, index) in article" :timestamp="item.time" placement="top">
        <el-card>
          <h4 class="title" @click="getArticleDetail(item.id)">{{item.title}}</h4>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
  export default {
    name: "Archive",
    mounted() {
      const _this = this;
      _this.categoryId = _this.$route.query.categoryId;
      _this.categoryName = _this.$route.query.categoryName;
      _this.tagId = _this.$route.query.tagId;
      _this.tagName = _this.$route.query.tagName;
      if (_this.categoryId) {
        _this.getRequest('/getArticleByCategoryId/' + _this.categoryId).then(value => {
          _this.article = value.data;
        })
      } else if (_this.tagId) {
        _this.getRequest('/getArticleByTagId/' + _this.tagId).then(value => {
          _this.article = value.data;
        })
      } else {
        _this.getAllArticleSubstringContent();
      }
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
      }
    },
    data() {
      return {
        article: [],
        categoryId: '',
        categoryName: '',
        tagId: '',
        tagName: '',
      }
    }
  }
</script>

<style scoped>
  .title {
    cursor: pointer;
  }
</style>
