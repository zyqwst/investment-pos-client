package com.sy.investment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sy.investment.interceptor.AuthorizationInterceptor;

import feign.Request;
import feign.Retryer;

@Configuration
public class MvcConfig implements WebMvcConfigurer   {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	InterceptorRegistration addInterceptor = registry.addInterceptor(authorizationInterceptor);
        
        addInterceptor.excludePathPatterns("/user/login",
        								   "/user/store",
        								   "/user/store-regist",
        								   "/error",
        								   "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
        								   "/",
        								   "",
        								   "/index")
        			  .addPathPatterns("/**");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
    public static final int connectTimeOutMillis = 10000;
    public static final int readTimeOutMillis = 10000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }
}