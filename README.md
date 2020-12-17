### 前言

这是一个个人博客系统，适合初学者学习使用。

由于本人是学生，技术有限，也是通过学习大佬的作品搭建了一个简单博客项目用来练手。

如果觉着对你有帮助欢迎给个**star**

Github地址https://github.com/doukangtai/springboot-vue-blog

前端项目地址https://github.com/doukangtai/vue-blog

### 技术栈

##### 后端

- Spring Boot
- Spring Security
- Mybatis
- MySQL

##### 前端

- Vue
- vue-router
- Vuex
- Element UI
- axios

### 效果图

可以根据标题查询、编辑、删除文章

![admin-article-list](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/admin-article-list.png)

使用Markdown写文章界面

![admin-write-article](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/admin-write-article.png)

文章列表页

![article-list](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/article-list.png)

文章详情页

![article-detail](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/article-detail.png)

时间线——归档

![timeline](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/timeline.png)

标签页

![tag](https://github.com/doukangtai/springboot-vue-blog/blob/master/resources/img/tag.png)

### 部署

1. clone到本地
2. 创建数据库，导入resources目录下的blog.sql文件
3. 将blog-boot导入IDEA
4. 将application.yml的内容修改为自己的
5. IDEA中启动项目，前端http://localhost:8081/#/后端http://localhost:8081/#/login
6. 需要二次开发前端可以用webstorm导入blog-vue
7. 安装依赖、运行即可http://localhost:8082/#/login
8. 二次开发完成可以打包后将前端项目生成的dist目录下的资源放进后端项目的resources/static目录下

### 分析项目

##### 后端

``` java
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<String> urlList = new ArrayList<>();
        urlList.add("/reg");
        urlList.add("/uploadImg");
        urlList.add("/addCategory");
        urlList.add("/addTag");
        urlList.add("/addArticle");
        urlList.add("/deleteArticleById/*");
        for (int i = 0; i < urlList.size(); i++) {
            if (antPathMatcher.match(urlList.get(i), requestUrl)) {
                return SecurityConfig.createList("ROLE_login");
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
```

获取当前请求路径，看当前请求路径是否为`urlList`中需要`ROLE_login`角色才能够访问，

若不是`urlList`中的请求路径则直接通过，

否则进行拦截，并添加需要的角色。

``` java
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            String needRole = iterator.next().getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (needRole.equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

```

获取`UrlFilterInvocationSecurityMetadataSource`组件中添加的角色，并与当前登录用户下的角色对比是否存在需要的角色，

若存在则通过，

否则抛出异常

``` xml
  <select id="loadUserByUsername" resultMap="BaseResultMap">
    SELECT *,'ROLE_login' role from user where username=#{username}
  </select>
```

通过sql语句添加了一个`ROLE_login`角色

``` java
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员!\"}");
        writer.flush();
        writer.close();
    }
}
```

发生403权限不足时的处理组件

``` java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/reg", "lg").permitAll().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        })
                .and().formLogin().loginPage("/loginPage").loginProcessingUrl("/lg").usernameParameter("username").passwordParameter("password").permitAll().successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                writer.write("{\"status\":\"success\",\"msg\":" + objectMapper.writeValueAsString(UserUtils.getCurrentUser()) + "}");
                writer.flush();
                writer.close();
            }
        }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("{\"status\":\"error\",\"msg\":\"");
                if (e instanceof UsernameNotFoundException) {
                    sb.append("此用户找不到");
                } else {
                    sb.append("登录失败");
                }
                sb.append("\"}");
                writer.write(sb.toString());
                writer.flush();
                writer.close();
            }
        })
                .and().logout().logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                Map<String, Object> map = new HashMap<>();
                map.put("status", "success");
                map.put("msg", "登出成功");
                ObjectMapper objectMapper = new ObjectMapper();
                writer.write(objectMapper.writeValueAsString(map));
                writer.flush();
                writer.close();
            }
        }).permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    }
}
```

将`UserService`、`CustomAccessDeniedHandler`、`UrlFilterInvocationSecurityMetadataSource`、`UrlAccessDecisionManager`组件添加进程序

添加登录成功、登录失败、登出成功时的返回信息

##### 前端

使用vuex存储登录后的用户信息store/index.js

utils/api.js封装了一些axios的请求方法，以及请求响应拦截器

其他的没什么好说的了

