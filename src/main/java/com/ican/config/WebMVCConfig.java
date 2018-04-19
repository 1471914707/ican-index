package com.ican.config;

import com.ican.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiInterceptor())
                .addPathPatterns("/api/*");
        registry.addInterceptor(new SchoolInterceptor())
                .addPathPatterns("/school")
                .addPathPatterns("/school/**");
        registry.addInterceptor(new CollegeInterceptor())
                .addPathPatterns("/college")
                .addPathPatterns("/college/**");
        registry.addInterceptor(new TeacherInterceptor())
                .addPathPatterns("/teacher")
                .addPathPatterns("/teacher/**");
        registry.addInterceptor(new StudentInterceptor())
                .addPathPatterns("/student")
                .addPathPatterns("/student/**");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin")
                .addPathPatterns("/admin/**");
    }
}
