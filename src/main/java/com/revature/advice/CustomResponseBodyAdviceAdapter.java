package com.revature.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.revature.services.LoggingService;

/**
 * Creates advice to convert ResponseBody's during method transfer to an endpoint into an unserialized form, in order to be read, and then back into a serialized form.
 * 
 * @author Timothy Mitchell
 *
 */

@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    
	@Autowired
    LoggingService loggingService;
    
	/**
	 * Checks to see if the particular request is supported or not.
	 * 
	 * @param methodParameter represents the parameter signifying which method is being used
	 * @param type is a generic reflections placeholder meant to be filled outside of the method
	 * @param aClass is a generic placeholder that specifies there will be a class that extends HttpMessageConverter
	 * @return returns a boolean of true
	 */
	
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
    
    /**
	 * Advice that specifies logging the response body before it is sent. Then it reconstructs the serialized form of the response.
	 * 
	 * @param o represents the placeholder for the response body
	 * @param methodParameter represents represents the parameter of the response body
	 * @param mediaType represents the type that is being sent from the web service
	 * @param aClass is a generic placeholder that specifies there will be a class that extends HttpMessageConverter
	 * @param serverHttpRequest represents the http request sent
	 * @param serverHttpResponse represents the http response to be sent
	 * @return returns the response body object
	 */
    
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            loggingService.logResponse(
                    ((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                    ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }
        
        return o;
    }
}