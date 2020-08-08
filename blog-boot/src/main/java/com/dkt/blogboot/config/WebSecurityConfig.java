package com.dkt.blogboot.config;

import com.dkt.blogboot.service.UserService;
import com.dkt.blogboot.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 窦康泰
 * @Date 2020-08-01 12:41
 */
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
