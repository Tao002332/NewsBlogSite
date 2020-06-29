package com.newsblogsite.friend.config;


import com.newsblogsite.friend.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * *@author 83614
 * *@date 2020/2/25
 **/

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        //配置拦截对象和拦截请求
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**");
    }

}
