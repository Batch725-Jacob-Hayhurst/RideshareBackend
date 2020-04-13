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

//import lombok.extern.log4j.Log4j2;

@Component
@Slf4j
public class LoggingServiceImpl implements LoggingService {
    
    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);
        
        stringBuilder.append("\n");
        stringBuilder.append("REQUEST \n");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] \n");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] \n");
        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] \n");
        
        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] \n");
        }
        
        if (body != null) {
            stringBuilder.append("body=[" + body + "] \n");
        }
        
        log.info("\n" + stringBuilder.toString());
    }
    
    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("\n");
        stringBuilder.append("RESPONSE \n");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] \n");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] \n");
        stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] \n");
        stringBuilder.append("responseBody=[").append(body).append("] \n");
        
        log.info("\n" + stringBuilder.toString());
    }
    
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
    
    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        
        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }
        
        return map;
    }
}
