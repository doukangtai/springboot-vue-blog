<template>
  <div class="block">
    <h2 class="header">分类</h2>
    <el-timeline>
      <el-timeline-item v-for="(item, index) in category" placement="bottom">
        <span class="category" @click="getArticleByCategory(item.id, item.category)">{{item.category}}</span>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
  export default {
    name: "DisplayCategory",
    mounted() {
      const _this = this;
      _this.getAllCategory();
    },
    methods: {
      getAllCategory() {
        const _this = this;
        this.getRequest('/getAllCategory').then(value => {
          if (value.status == '200') {
            _this.category = value.data;
          }
        });
      },
      getArticleByCategory(id, category) {
        this.$router.push({path: '/archive', query: {categoryId: id, categoryName: category}});
      },
    },
    data() {
      return {
        category: [],
      }
    }
  }
</script>

<style scoped>
  .header {
    text-align: center;
    font-weight: 500;
  }

  .category {
    cursor: pointer;
  }
</style>
