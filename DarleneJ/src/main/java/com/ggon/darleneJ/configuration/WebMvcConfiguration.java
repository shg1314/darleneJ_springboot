package com.ggon.darleneJ.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ggon.darleneJ.configuration.logger.LoggerInterceptor;
import com.ggon.darleneJ.configuration.auth.AuthInterceptor;
import com.ggon.darleneJ.configuration.controller.CustomMapArgumentResolver;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); //5 * 1024 * 1024 (5mb)
		return commonsMultipartResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new AuthInterceptor());//todo
		//registry.addInterceptor(new LoggerInterceptor()); //todo
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		        argumentResolvers.add(new CustomMapArgumentResolver());
	}
}

