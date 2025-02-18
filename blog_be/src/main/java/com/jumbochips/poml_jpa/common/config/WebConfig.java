package com.jumbochips.poml_jpa.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String ALLOW_CROSS_ORIGIN_DOMAIN;

    @Value("${cors.allowed-methods}")
    private String[] ALLOW_METHODS;

    @Value("${image.base-upload-dir}")
    private String baseUploadDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALLOW_CROSS_ORIGIN_DOMAIN)
                .allowedMethods(ALLOW_METHODS)
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = System.getenv("IMAGE_UPLOAD_DIR"); // 환경변수에서 가져오기
        if (uploadDir == null || uploadDir.isEmpty()) {
            uploadDir = "/uploads";  // 기본값 설정
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }


}
