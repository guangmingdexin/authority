package com.authority.common.first.filter;


import com.authority.common.utils.Msg;
import com.authority.common.utils.token.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.naming.AuthenticationNotSupportedException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

// 鉴权

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
            // 如果请求头中没有Authorization信息则直接放行（说明是不需要鉴权的权限）
            if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
                // 交给下一个过滤器
                chain.doFilter(request, response);
                return;
            }
            // 如果请求头中有token,则进行解析，并且设置认证信息
            try {
                // 认证通过后用户信息将会保存在线程上下文中
                String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
                String uid = JwtTokenUtils.getUserId(token);

                System.out.println("uid: " + uid);
                Object oldTokenObj = redisTemplate.opsForValue().get("access_token:member_"+uid);

                if(oldTokenObj != null && token.equals(oldTokenObj.toString())) {
                    SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
                    super.doFilterInternal(request, response, chain);
                }else {
                    throw new AuthenticationNotSupportedException("授权失败，重新登陆！");
                }

            }catch (ExpiredJwtException e) {
                System.out.println("token 过期了！");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().write(mapper.writeValueAsString(Msg.setResult("401", null, "token 过期，请重新登陆")));
            }catch (AuthenticationNotSupportedException e) {
                System.out.println("单一登陆");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().write(mapper.writeValueAsString(Msg.setResult("401", null, e.getMessage())));
            }catch (MalformedJwtException e) {
                System.out.println(" 无效 token");
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String username = JwtTokenUtils.getUsername(token);

        String role = JwtTokenUtils.getUserRole(token);

        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(role)));
        }
        return null;
    }

}
