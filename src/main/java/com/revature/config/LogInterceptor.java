package com.revature.config;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.revature.services.LoggingService;

/**
* LogInterceptor is a custom implementation of Spring Boot's HandlerInterceptor. It pulls requests and responses in
* transit from their final location and passes them through a logging service to log all request and/or response details.
* 
* @author Judson Higley
*
*/

@Component
public class LogInterceptor implements HandlerInterceptor {
    
	@Autowired
    LoggingService loggingService;
    
    /**
	 * Retrieves the request and response prior to handling and passes them to the logging service
	 * to be logged.
	 * 
	 * @param request is the HttpServletRequest sent in
	 * @param response is the HttpServletResponse sent back out
	 * @param handler is used to handle what gets handled in the logging
	 * @return returns a boolean of true
	 */
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                             Object handler) {
        
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }
        
        return true;
    }
}