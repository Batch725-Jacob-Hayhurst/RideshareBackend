package com.revature.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoggingService is an interface that formats the logging service methods.
 * 
 * @author Timothy Mitchell
 *
 */

public interface LoggingService {
    
    void logRequest(HttpServletRequest httpServletRequest, Object body);
    
    void logResponse(HttpServletRequest httpServletRequest, 
                     HttpServletResponse httpServletResponse, 
                     Object body);
}
