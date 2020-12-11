package com.authority.common.config.security;

import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.po.Role;
import com.authority.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName FilterMetadataSource
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 22:31
 * @Version 1.0
 **/
@Component
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private MenuService menuService;

    // 用来实现 ant 风格的 url 匹配
    AntPathMatcher antPathMatcher =new AntPathMatcher() ;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取 url
        String requestUrl = ((FilterInvocation) o) .getRequestUrl ();
        System.out.println("requestUrl: " + requestUrl);

        List<MenuToRole> allMenus = menuService.getAllMenusToRole();

        for (MenuToRole menu : allMenus) {
            if(antPathMatcher.match(menu.getMenuPatten(), requestUrl)) {
                System.out.println("menu_patten: " + menu.getMenuPatten());

                // 对每个 menu 查询 所有角色
                List<Role> roles = menu.getRoles();

                String[] roleNames = new String[roles.size()];

                for (int i = 0; i < roleNames.length; i++) {
                    roleNames[i] = roles.get(i).getRoleName();
                }
                System.out.println("查询到的角色： " + Arrays.toString(roleNames));
                return SecurityConfig.createList(roleNames);
            }

        }
        // 登陆即可访问
        System.out.println("设置默认访问角色");
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
