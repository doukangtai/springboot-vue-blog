package com.dkt.blogboot.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-01 11:15
 */
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
