package com.authority.common.first.filter;

import com.authority.common.utils.login.LoginUtil;
import com.authority.pojo.po.Admin;
import com.authority.pojo.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * 用户登陆验证
 *
 * @author 光明的心
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

   // private AuthenticationManager authenticationManager;


//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        super.setFilterProcessesUrl("/admins/login");
//    }

    public JWTAuthenticationFilter() {
        super.setFilterProcessesUrl("/admins/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("开始登陆了！");
        // 从输入流中获取登陆的信息
        try {
            ServletInputStream inputStream = request.getInputStream();

            // 输入缓寸
            BufferedInputStream buf = new BufferedInputStream(inputStream);

            String contentLen = request.getHeader("Content-Length");

            byte[] bytes;
            if(contentLen != null) {
                bytes = new byte[Integer.parseInt(contentLen)];
            }else {
                // 使用 默认值
                throw new ArithmeticException("POST 请求没有Content-Length字段");
            }

            // 已经读取的字节数
            int bytesRead = 0;
            // 需要读取的字节数
            int bytesToRead = bytes.length;
            while (bytesRead < bytesToRead) {
                bytesRead += buf.read(bytes, bytesRead, bytesToRead);
            }

            // 转换为 Java 对象
            ObjectMapper mapper = new ObjectMapper();

            User user = mapper.readValue(bytes, Admin.class);

            return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),
                    user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        LoginUtil.success(response, authResult);
    }

    /**
     *
     * 验证失败的回调
     *
     * @param request 输入流
     * @param response 输出流
     * @param failed
     * @throws IOException
     */

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        LoginUtil.fail(response, failed);
    }
}
