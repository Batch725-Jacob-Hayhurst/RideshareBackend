package com.revature.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Sets up the configuration for the log interceptor dealing with http requests and responses.
 * 
 * @author Timothy Mitchell
 *
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    
    @Autowired
    LogInterceptor logInterceptor;
    
    /**
	 * Adds the log interceptor to the interceptor registry in order to log requests and responses.
	 * 
	 * @param register represents the InterceptorRegistry
	 */
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }
}
