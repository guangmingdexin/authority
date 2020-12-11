package com.authority.service.user.admin;

import com.authority.dao.mapper.admin.AdminUserMapper;
import com.authority.pojo.bo.JwtUser;
import com.authority.pojo.po.Admin;
import com.authority.pojo.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 19:14
 * @Version 1.0
 **/
@Service
@Qualifier("adminUserService")
public class AdminUserService implements UserDetailsService {

    @Autowired
    private AdminUserMapper admin;

    /**
     * 管理员登陆时自动调用
     *
     * @param userName 管理员用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("自动验证管理员用户名： 密码");
        // 通过用户名获取到 用户信息
        Admin user = admin.loadUserByUsername(userName);

        if(user == null) {
            return new JwtUser();
        }

        // 通过查找到 的 roleId 获取 用户
        List<Role> roles = admin.getRoles(user.getUserId());

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        // 不可更改
        authorities = Collections.unmodifiableList(authorities);
        return new JwtUser(user, authorities);
    }
}
