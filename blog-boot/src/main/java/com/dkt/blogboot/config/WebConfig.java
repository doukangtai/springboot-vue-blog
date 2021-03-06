package com.dkt.blogboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");
        if ("Windows 10".equals(osName)) {
            registry.addResourceHandler("/upload/image/**").addResourceLocations("file:" + new File(uploadImgPathWindows).getAbsolutePath() + "/");
        } else {
            registry.addResourceHandler("/upload/image/**").addResourceLocations("file:" + new File(uploadImgPathLinux).getAbsolutePath() + "/");
        }
    }
}
