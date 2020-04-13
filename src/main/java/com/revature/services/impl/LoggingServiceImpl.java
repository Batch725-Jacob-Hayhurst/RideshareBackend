package com.revature.services.impl;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.revature.services.LoggingService;

/**
 * LoggingServiceImpl takes in the http response or request along with an object called "body" that corresponds to the respective request or response.
 * It then logs the request or response.
 * 
 * @author Timothy Mitchell
 *
 */

@Component
@Slf4j
public class LoggingServiceImpl implements LoggingService {
    
	/**
	 * Takes in the request and request body object from the CustomRequestBodyAdviceAdapter. It the creates a string builder and a map that maps parameters
	 * of the http request. After this, it appends the request information to the string builder. If the parameter map is empty, it attaches the parameters
	 * to the string builder. If the body is not null, the body is added to the string builder and then the request is logged.
	 * 
	 * @param httpServletRequest represents the http request sent
	 * @param body represents the extracted object representing the request body
	 */
	
    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);
        
        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");
        
        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }
        
        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }
        
        log.info(stringBuilder.toString());
    }
    
    /**
	 * Takes in the response and response body object from the CustomResponseBodyAdviceAdapter. It the creates a string builder and a map that maps parameters
	 * of the http response. After this, it appends the response information to the string builder. If the parameter map is empty, it attaches the parameters
	 * to the string builder. If the body is not null, the body is added to the string builder and then the response is logged.
	 * 
	 * @param httpServletRequest represents the http request sent
	 * @param httpServletResponse represents the http response to be sent
	 * @param body represents the object of the response body
	 */
    
    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("RESPONSE ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
        stringBuilder.append("responseBody=[").append(body).append("] ");
        
        log.info(stringBuilder.toString());
    }
    
    /**
	 * Creates the map of parameters pertaining to each http request. 
	 * 
	 * @param httpServletReqeust represents the http request sent
	 * @return returns the map of the parameters
	 */
    
    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }
        
        return resultMap;
    }
    
    /**
	 * Creates the map of the request headers
	 * 
	 * @param request represents the http request sent
	 * @return returns the map of the headers for the request
	 */
    
    @SuppressWarnings("rawtypes")
	private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        
        return map;
    }
    
    /**
	 * Creates the map of the response headers
	 * 
	 * @param response represents the http response to be sent
	 * @return returns the map of the headers for the response
	 */
    
    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        
        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }
        
        return map;
    }
}
