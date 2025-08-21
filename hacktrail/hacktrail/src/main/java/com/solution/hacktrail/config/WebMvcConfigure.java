package com.solution.hacktrail.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5174")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:/D:/Java/");
//    }

}
