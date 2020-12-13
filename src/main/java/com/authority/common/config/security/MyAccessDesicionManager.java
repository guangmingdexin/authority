package com.authority.common.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * @ClassName MyAccessDesicionManager
 * @Description 判断当前用户是否有请求 当前 url 的权限
 * @Author guangmingdexin
 * @Date 2020/12/4 0:48
 * @Version 1.0
 **/
@Component
public class MyAccessDesicionManager implements AccessDecisionManager {

    /**
     * @param authentication 当前登陆用户的信息
     * @param o 当前请求对象
     * @param collection 当前请求 url 所需要的角色权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authes = authentication.getAuthorities();

        System.out.println(o.toString());
        for (ConfigAttribute attribute : collection) {

            System.out.println("请求当前url所需的角色权限： " + attribute.getAttribute());
            if("ROLE_LOGIN".equals(attribute.getAttribute()) && authentication instanceof UsernamePasswordAuthenticationToken) {
                return;
            }

            for (GrantedAuthority authority : authes) {
                System.out.println("当前登陆用户角色： " + authority.getAuthority());

                if(attribute.getAttribute().equals(authority.getAuthority())) {
                    System.out.println("验证成功，可以进入下一步");
                    return;
                }
            }
        }

        throw new AccessDeniedException ("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
