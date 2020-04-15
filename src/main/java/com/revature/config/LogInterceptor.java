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
* LogInterceptor handles and logs all HttpServletRequests and Responses.
* 
* @author Timothy Mitchell
*
*/

@Component
public class LogInterceptor implements HandlerInterceptor {
    
	@Autowired
    LoggingService loggingService;
    
    /**
	 * Filters the requests and responses and logs exceptions if they are encountered.
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