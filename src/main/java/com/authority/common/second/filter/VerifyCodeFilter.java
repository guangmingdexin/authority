package com.authority.common.second.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 前后端分离，带校验码，后台生产校验码，前台发送
 *
 * @ClassName VerifyCodeFilter
 * @Author guangmingdexin
 * @Date 2021/7/5 18:58
 * @Version 1.0
 **/
public class VerifyCodeFilter extends GenericFilterBean {

    private final static String defaultFilterProcessUrl = "/account/login";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            // 验证码验证
            String requestCaptcha = request.getParameter("code");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String genCaptcha = (String) request.getSession().getAttribute("index_code");
            if (StringUtils.isEmpty(requestCaptcha)) {
                throw new AuthenticationServiceException("验证码不能为空!");
            }
            if (!genCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase())) {
                throw new AuthenticationServiceException("验证码错误!");
            }
        }
        chain.doFilter(request, response);
    }
}
