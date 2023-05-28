package com;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(true)
		.setUseTrailingSlashMatch(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/admin/category/**").addResourceLocations("/admin/category/");
    	registry.addResourceHandler("/admin/commenta/**").addResourceLocations("/admin/commenta/");
    	registry.addResourceHandler("/admin/common/**").addResourceLocations("/admin/common/");
    	registry.addResourceHandler("/admin/index/**").addResourceLocations("/admin/index/");
    	registry.addResourceHandler("/admin/login/**").addResourceLocations("/admin/login/");
    	registry.addResourceHandler("/admin/movie/**").addResourceLocations("/admin/movie/");
    	registry.addResourceHandler("/admin/user/**").addResourceLocations("/admin/user/");
    	registry.addResourceHandler("/index/commenta/**").addResourceLocations("/index/commenta/");
    	registry.addResourceHandler("/index/moviea/**").addResourceLocations("/index/moviea/");
    	registry.addResourceHandler("/common/cmm/**").addResourceLocations("/common/cmm/");
    }
}