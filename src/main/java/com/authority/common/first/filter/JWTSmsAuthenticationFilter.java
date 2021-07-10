package com.authority.common.first.filter;

import com.authority.common.first.provider.SmsAuthenticationFilter;
import com.authority.common.first.provider.SmsAuthenticationToken;
import com.authority.common.utils.login.LoginUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @ClassName JWTSmsAuthenticationFilter
 * @Author guangmingdexin
 * @Date 2021/7/5 16:49
 * @Version 1.0
 **/

public class JWTSmsAuthenticationFilter extends SmsAuthenticationFilter {

 //   private AuthenticationManager authenticationManager;


//    public JWTSmsAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        //super.setFilterProcessesUrl("/sms/login");
//    }

    public JWTSmsAuthenticationFilter() {
        super.setFilterProcessesUrl("/sms/login");
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("验证自定义的登陆过滤器！");
        // 判断不为空
        if(!"POST".equals(request.getMethod())) {
            unsuccessfulAuthentication(request, response,  new BadCredentialsException("请求方法错误！"));
        }

        int len = request.getContentLength();
        if(len == 0) {
            unsuccessfulAuthentication(request, response,  new BadCredentialsException("请求方法错误！"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //
        Map sms = objectMapper.readValue(request.getInputStream(), Map.class);

        System.out.println("tel: " + sms.get("tel"));
        System.out.println("code:" + sms.get("code"));

        return this.getAuthenticationManager().authenticate(new SmsAuthenticationToken(sms.get("tel"),
                sms.get("code"), new ArrayList<>()));

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUtil.success(response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        LoginUtil.fail(response, failed);
    }

}
