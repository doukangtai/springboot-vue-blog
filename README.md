### 前言

这是一个个人博客系统，适合初学者学习使用。

由于本人是学生，技术有限，也是通过学习大佬的作品搭建了一个简单博客项目用来练手。

如果觉着对你有帮助欢迎给个**star**

Github地址https://github.com/doukangtai/springboot-vue-blog

前端项目地址https://github.com/doukangtai/vue-blog

### 技术栈

##### 后端

- Spring Boot
- MyBatis
- Redis
- MySQL

##### 前端

- Vue
- vue-router
- Vuex
- Element UI
- axios

### 效果图

首页展示文章分页数据

![首页](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E9%A6%96%E9%A1%B5.png)

按时间线展示

![归档](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E5%BD%92%E6%A1%A3.png)

展示分类

![分类](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E5%88%86%E7%B1%BB.png)

展示标签

![标签](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E6%A0%87%E7%AD%BE.png)

Markdown写文章界面

![写文章](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E5%86%99%E6%96%87%E7%AB%A0.png)

文章列表界面

![文章列表](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E6%96%87%E7%AB%A0%E5%88%97%E8%A1%A8.png)

管理分类、标签

![标签分类管理](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/%E6%A0%87%E7%AD%BE%E5%88%86%E7%B1%BB%E7%AE%A1%E7%90%86.png)

### 部署

1. clone到本地
2. 创建数据库，导入resources目录下的blog.sql文件
3. 将blog-boot导入IDEA，maven下载依赖包
4. 将application.yml的内容修改为自己的
5. clone前端项目https://github.com/doukangtai/vue-blog导入WebStorm
6. npm安装依赖
7. IDEA中启动后端项目、WebStorm中启动前端项目
8. 访问前端项目
