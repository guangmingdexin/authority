package com.authority.common.first.filter;

import com.authority.common.first.provider.AccountAuthenticationFilter;
import com.authority.common.first.provider.AccountAuthenticationToken;
import com.authority.common.utils.login.LoginUtil;
import com.authority.pojo.po.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName JWTAccountAuthenticationFilter
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/5 18:58
 * @Version 1.0
 **/

public class JWTAccountAuthenticationFilter extends AccountAuthenticationFilter {

  //  private AuthenticationManager authenticationManager;

//    public JWTAccountAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//       // super.setFilterProcessesUrl("/account/login");
//    }

    public JWTAccountAuthenticationFilter() {
        super.setFilterProcessesUrl("/account/login");
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
        Account account = objectMapper.readValue(request.getInputStream(), Account.class);
        // 直接判断验证码是否正确
        String requestCaptcha = account.getCode();
        String genCaptcha = (String) request.getSession().getAttribute("index_code");
        // session 如何管理
        if(requestCaptcha == null || !requestCaptcha.equalsIgnoreCase(genCaptcha)) {
            System.out.println("reqCap: " + requestCaptcha);
            System.out.println("genCap" + genCaptcha);
            // 校验失败
            unsuccessfulAuthentication(request, response, new AuthenticationServiceException("验证码错误！"));
         //   request.getSession().removeAttribute("index_code");
            return null;
        }

        return this.getAuthenticationManager().authenticate(new AccountAuthenticationToken(account.getUserName(),
                account.getPassword(), new ArrayList<>()));

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
