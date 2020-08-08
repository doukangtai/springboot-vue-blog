<template>
  <div>
    <span class="category">请选择分类</span>
    <template>
      <el-select
          v-model="valueCategory"
          filterable
          default-first-option
          placeholder="请选择文章分类">
        <el-option
            v-for="item in optionsCategory"
            :key="item.id"
            :label="item.category"
            :value="item.id">
        </el-option>
      </el-select>
    </template>
    <el-button type="text" @click="dialogVisible = true">添加分类</el-button>

    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form :model="categoryForm" :rules="rulesCategory" ref="categoryForm" label-width="100px"
               class="demo-ruleForm">
        <el-form-item label="分类名称" prop="category">
          <el-input v-model="categoryForm.category"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addCategory">添加</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <span class="tag">请选择标签</span>
    <template>
      <el-select
          v-model="valueTag"
          multiple
          filterable
          default-first-option
          placeholder="请选择文章标签">
        <el-option
            v-for="item in optionsTag"
            :key="item.id"
            :label="item.tag"
            :value="item.id">
        </el-option>
      </el-select>
    </template>
    <el-button type="text" @click="dialogVisibleTag = true">添加标签</el-button>

    <el-dialog
        title="提示"
        :visible.sync="dialogVisibleTag"
        width="30%"
        :before-close="handleCloseTag">
      <el-form :model="tagForm" :rules="rulesTag" ref="tagForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标签名称" prop="tag">
          <el-input v-model="tagForm.tag"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addTag">添加</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row>
      <el-input class="title" v-model="title" placeholder="请输入文章标题"></el-input>
    </el-row>
    <mavon-editor class="mk" v-model="articleContent" ref=md @imgAdd="$imgAdd"></mavon-editor>
    <el-row>
      <el-button @click="addArticle" type="success" round>发表文章</el-button>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "WriteArticle",
    mounted() {
      const _this = this;
      this.getAllCategory();
      this.getAllTag();
      const id = _this.$route.query.id;
      if (id != undefined) {
        _this.getArticleById(id);
      }
    },
    methods: {
      // 绑定@imgAdd event
      $imgAdd(pos, $file) {
        // 第一步.将图片上传到服务器.
        var formdata = new FormData();
        formdata.append('image', $file);
        const _this = this;
        _this.uploadFileRequest('/uploadImg', formdata).then((url) => {
          // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
          /**
           * $vm 指为mavonEditor实例，可以通过如下两种方式获取
           * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
           * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
           */
          if (url.data.status == 'success') {
            _this.$refs.md.$img2Url(pos, url.data.msg);
          } else {
            _this.$message({type: url.data.status, message: url.data.msg});
          }
        })
      },
      getAllCategory() {
        const _this = this;
        this.getRequest('/getAllCategory').then(value => {
          if (value.status == '200') {
            _this.optionsCategory = value.data;
          }
        });
      },
      getAllTag() {
        const _this = this;
        this.getAllCategory();
        this.getRequest('/getAllTag').then(value => {
          if (value.status == '200') {
            _this.optionsTag = value.data;
          }
        });
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },
      handleCloseTag(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },
      addCategory() {
        const _this = this;
        _this.postRequest('/addCategory', {
          category: this.categoryForm.category,
        }).then(value => {
          if (value.status == '200') {
            if (value.data.status == 'success') {
              this.dialogVisible = false;
              this.categoryForm.category = '';
              _this.getAllCategory();
            } else {
              _this.$message.error({message: value.data.msg});
            }
          }
        });
      },
      addTag() {
        const _this = this;
        _this.postRequest('/addTag', {
          tag: this.tagForm.tag,
        }).then(value => {
          if (value.status == '200') {
            if (value.data.status == 'success') {
              this.dialogVisibleTag = false;
              this.tagForm.tag = '';
              _this.getAllTag();
            } else {
              _this.$message.error({message: value.data.msg});
            }
          }
        });
      },
      addArticle() {
        const _this = this;
        _this.postRequest('/addArticle', {
          categoryId: _this.valueCategory,
          tagIdArr: _this.valueTag,
          title: _this.title,
          content: _this.articleContent,
          id: _this.articleId,
        }).then(value => {
          console.log(value);
          if (value.status == 200 && value.data.status == 'success') {
            _this.$message.success({message: value.data.msg});
            _this.valueCategory = '';
            _this.valueTag = [];
            _this.title = '';
            _this.articleContent = '';
          } else {
            _this.$message.error({message: value.data.msg});
          }
        })
      },
      getArticleById(id) {
        const _this = this;
        _this.getRequest('/getArticleById/' + id).then(value => {
          const date = value.data;
          _this.articleId = date.id;
          _this.title = date.title;
          _this.articleContent = date.content;
          _this.valueCategory = date.category.id;
          for (let i = 0; i < date.tags.length; i++) {
            _this.valueTag.push(date.tags[i].id);
          }
        })
      }
    },
    data() {
      return {
        optionsCategory: [],
        valueCategory: '',
        optionsTag: [],
        valueTag: [],
        title: '',
        articleContent: '',
        articleId: '-1',
        dialogVisible: false,
        dialogVisibleTag: false,
        categoryForm: {
          category: '',
        },
        tagForm: {
          tag: '',
        },
        rulesCategory: {
          category: [
            {required: true, message: '请输入分类名称', trigger: 'blur'},
          ],
        },
        rulesTag: {
          tag: [
            {required: true, message: '请输入标签名称', trigger: 'blur'},
          ],
        }
      }
    }
  }
</script>

<style scoped>
  .tag, .category {
    margin-left: 10px;
    margin-right: 10px;
  }

  .mk {
    margin-top: 10px;
    margin-bottom: 10px;
  }

  .title {
    margin-top: 10px;
  }
</style>
