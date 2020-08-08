<template>
  <div>
    <div>
      <el-input class="titleInput" v-model="searchTitle" placeholder="请输入文章标题"></el-input>
      <el-button class="searchBtn" @click="getArticleByTitle" type="primary" round>查询</el-button>
    </div>
    <template>
      <el-table
          height="540"
          :data="articleData"
          style="width: 100%">
        <el-table-column
            fixed
            prop="id"
            label="编号"
            width="50">
        </el-table-column>
        <el-table-column
            prop="title"
            label="标题"
            width="280">
        </el-table-column>
        <el-table-column
            prop="category.category"
            label="分类"
            width="150">
        </el-table-column>
        <el-table-column
            prop="city"
            label="标签"
            width="380">
          <template slot-scope="scope">
            <el-tag class="tag" type="success" v-for="(tag, index) in scope.row.tags">{{tag.tag}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="time"
            label="发表日期"
            width="160">
        </el-table-column>
        <el-table-column
            label="操作"
            width="200">
          <template slot-scope="scope">
            <el-button @click="editArticle(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteArticle(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
  </div>
</template>

<script>
  export default {
    name: "ArticleList",
    mounted() {
      this.getAllArticle();
    },
    methods: {
      editArticle(row) {
        this.$router.push({path: '/writeArticle', query: {id: row.id}});
      },
      deleteArticle(row) {
        const _this = this;
        _this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.deleteRequest('/deleteArticleById/' + row.id).then(value => {
            if (value.status == 200 && value.data.status == 'success') {
              _this.$message.success({message: value.data.msg});
              _this.getAllArticle();
            } else {
              _this.$message.error({message: value.data.msg})
            }
          });
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      getAllArticle() {
        const _this = this;
        _this.getRequest('/getAllArticle').then(value => {
          if (value.status == 200) {
            _this.articleData = value.data
          } else {
            _this.$message.error({message: "初始化数据失败"})
          }
        })
      },
      getArticleByTitle() {
        const _this = this;
        if (_this.searchTitle == '') {
          _this.getAllArticle();
        } else {
          _this.getRequest('/getArticleByTitle/' + _this.searchTitle).then(value => {
            if (value.status == 200) {
              _this.articleData = value.data;
            } else {
              _this.$message.error({message: "初始化数据失败"})
            }
          });
        }
      }
    },
    data() {
      return {
        searchTitle: '',
        articleData: [],
        tagString: '',
      }
    }
  }
</script>

<style scoped>
  .titleInput, .searchBtn {
    float: left;
    margin: 10px 10px 10px 0px;
  }

  .titleInput {
    width: 300px;
  }

  .tag {
    margin: 0px 3px 0px 3px;
  }
</style>
