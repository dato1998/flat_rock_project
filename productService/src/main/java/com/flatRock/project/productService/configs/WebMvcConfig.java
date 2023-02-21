package com.flatRock.project.productService.configs;

import com.flatRock.project.productService.interceptor.RequestValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
     final RequestValidationInterceptor requestValidationInterceptor;

    public WebMvcConfig(RequestValidationInterceptor requestValidationInterceptor) {
        this.requestValidationInterceptor = requestValidationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestValidationInterceptor)
                .addPathPatterns("/**");
    }
}
