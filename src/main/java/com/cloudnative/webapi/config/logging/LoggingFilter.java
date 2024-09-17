package com.cloudnative.webapi.config.logging;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;

public class LoggingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            ThreadContext.put("ipAddress", getClientIp(httpServletRequest));
            ThreadContext.put("userAgent", httpServletRequest.getHeader("User-Agent"));

            String requestURI = httpServletRequest.getRequestURI();
            if (!requestURI.startsWith("/healthz")) {
                String httpMethod = httpServletRequest.getMethod();
                String queryParams = httpServletRequest.getQueryString();

                if (queryParams != null && !queryParams.isEmpty()) {
                    logger.info("Received {} request for {} with query params {}", httpMethod, requestURI, queryParams);
                } else {
                    logger.info("Received {} request for {}", httpMethod, requestURI);
                }
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            ThreadContext.clearAll();
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
