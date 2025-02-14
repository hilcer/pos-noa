package com.noa.pos.appweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebResourcesConfigure implements WebMvcConfigurer {

    @Value("${app.upload.images.path}")
    public String pathImages;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/product_img/**") // URL de acceso
                .addResourceLocations("file:" + pathImages + File.separator); // Ruta en el sistema de archivos
    }
}
