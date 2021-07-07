package com.dkt.blogboot.config;

import com.dkt.blogboot.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author 窦康泰
 * @date 2021/03/15
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload-img-path.windows}")
    String uploadImgPathWindows;
    @Value("${upload-img-path.linux}")
    String uploadImgPathLinux;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");
        if ("Windows 10".equals(osName)) {
            registry.addResourceHandler("/upload/image/**").addResourceLocations("file:" + new File(uploadImgPathWindows).getAbsolutePath() + "/");
        } else {
            registry.addResourceHandler("/upload/image/**").addResourceLocations("file:" + new File(uploadImgPathLinux).getAbsolutePath() + "/");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
        .excludePathPatterns("/user/**");

//                .addPathPatterns(
//                        "/article/insert/**",
//                        "/article/delete/**",
//                        "/article/upload/**",
//                        "/category/add/**",
//                        "/category/delete/**",
//                        "/tag/add/**",
//                        "/tag/delete/**"
//                );
    }
}
