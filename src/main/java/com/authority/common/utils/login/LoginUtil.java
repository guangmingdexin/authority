package com.authority.common.utils.login;

import com.authority.common.utils.Msg;
import com.authority.common.utils.token.JwtTokenUtils;
import com.authority.pojo.bo.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName LoginUtil
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/8 10:42
 * @Version 1.0
 **/
public class LoginUtil {

    public static void success(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();

        String role = "";
        // 每个用户在某一个时间内只有一个角色
        // 如果有多个角色 需要重新设计
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), role);
        // 返回创建成功的token
        // 按照jwt的规定，最后请求的格式应该是"Bearer token"
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
        response.getWriter().write(mapper.writeValueAsString(Msg.setResult("200",
                new JwtUser(jwtUser.getId(), jwtUser.getUsername(),
                        jwtUser.isEnable(), (List<GrantedAuthority>) jwtUser.getAuthorities()),
                "登陆成功！", token)));

    }


    public static void fail(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if(failed instanceof BadCredentialsException) {
            response.getWriter().write(mapper.writeValueAsString(Msg.setResult("1001", null, "用户名或者密码错误！")));
        }else if(failed instanceof LockedException){
            System.out.println(failed.getMessage());
            response.getWriter().write(mapper.writeValueAsString(Msg.setResult("1002", null, "当前用户被锁定！")));
        }else if(failed instanceof DisabledException){
            System.out.println(failed.getClass());
            response.getWriter().write(mapper.writeValueAsString(Msg.setResult("1003", null, "当前账户不可用！")));
        }else {
            response.getWriter().write(mapper.writeValueAsString(Msg.setResult("1004", null, "其他错误，自己想办法处理！")));
        }
    }
}
