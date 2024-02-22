package com.ltl.weba.filter;

import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/**", filterName = "loginFilter")
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String serverHost = "http://localhost:8080";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getParameter("token");
        logger.info("token: " + token);

        if (this.check(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String redirect = serverHost + "/sso/login?redirect=" + request.getRequestURL();
            response.sendRedirect(redirect);
        }
    }

    private boolean check(String jwt) {
        try {
            if (jwt == null || jwt.trim().length() == 0) {
                return false;
            }
            String object = HttpUtil.get(serverHost + "/sso/checkJwt?token=" + jwt);
            return ! StringUtils.isEmpty(object);
        } catch (Exception e) {
            logger.error("向认证中心请求失败", e);
            return false;
        }

    }
}