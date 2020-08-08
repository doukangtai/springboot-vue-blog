<template>
  <div class="dtag">
    <h2 class="header">标签</h2>
    <div class="tag-container">
      <el-tag @click="getArticleByTagId(item.id, item.tag)" class="tag" effect="plain" type="info" v-for="(item, index) in tag">{{item.tag}}</el-tag>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DisplayTag",
    mounted() {
      const _this = this;
      _this.getAllTag();
    },
    methods: {
      getAllTag() {
        const _this = this;
        this.getRequest('/getAllTag').then(value => {
          if (value.status == '200') {
            _this.tag = value.data;
          }
        });
      },
      getArticleByTagId(id, tag) {
        this.$router.push({path: '/archive', query: {tagId: id, tagName: tag}});
      },
    },
    data() {
      return {
        tag: [],
      }
    }
  }
</script>

<style scoped>
  .dtag {
    text-align: center;
  }

  .header {
    text-align: center;
    font-weight: 500;
  }

  .tag-container {
    margin: 0 50px;
  }

  .tag {
    margin: 5px 10px;
    cursor: pointer;
  }

  .tag:hover {
    text-decoration: underline;
    color: red;
    border-color: red;
  }
</style>
