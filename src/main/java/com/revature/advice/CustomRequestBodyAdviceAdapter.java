package com.revature.advice;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.revature.services.LoggingService;

/**
 * Creates advice to convert RequestBody's during method transfer to an endpoint into an unserialized form, in order to be read, and then back into a serialized form.
 * 
 * @author Timothy Mitchell
 *
 */


@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    
	@Autowired
    LoggingService loggingService;
    
	@Autowired
    HttpServletRequest httpServletRequest;
    
	/**
	 * Checks to see if the particular request is supported or not.
	 * 
	 * @param methodParameter represents the parameter signifying which method is being used
	 * @param type is a generic reflections placeholder meant to be filled outside of the method
	 * @param aClass is a generic placeholder that specifies there will be a class that extends HttpMessageConverter
	 * @return returns a boolean of true
	 */
	
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, 
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
    
    /**
	 * Advice that specifies logging the request body after it is read. Then it reconstructs the serialized form of the request.
	 * 
	 * @param body represents the request body
	 * @param inputMessage represents the message from the body of the request
	 * @param parameter represents the parameter of the request body
	 * @param targetType represents what type the method is converting the JSON to and from
	 * @param converterType is a generic placeholder that specifies there will be a class that extends HttpMessageConverter
	 * @return returns the serialized form of the request
	 */
    
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        
        loggingService.logRequest(httpServletRequest, body);
        
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
