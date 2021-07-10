package com.authority.service.user.account;

import com.authority.dao.mapper.account.AccountMapper;
import com.authority.pojo.bo.JwtUser;
import com.authority.pojo.po.Account;
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
 * 获取短信验证码接口
 *
 * @ClassName SmsUserService
 * @Author guangmingdexin
 * @Date 2021/7/5 16:17
 * @Version 1.0
 **/
@Service
@Qualifier("smsUserService")
public class SmsUserService implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String tel) throws UsernameNotFoundException {
        // 1.获取短信验证码
        String code = "1234";
        // 通过用户名获取到 用户信息
        Account user = accountMapper.loadUserByUsername(tel);
        System.out.println("user: " + user);
        if(user == null) {
            return new JwtUser();
        }
        user.setPassword(code);
        // 通过查找到 的 roleId 获取 用户
        List<Role> roles = accountMapper.getRoles(user.getUserId());

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        // 不可更改
        authorities = Collections.unmodifiableList(authorities);
        return new JwtUser(user, authorities);
    }
}
