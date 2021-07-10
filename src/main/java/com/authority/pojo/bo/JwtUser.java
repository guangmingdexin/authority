package com.authority.pojo.bo;

import com.authority.pojo.po.Account;
import com.authority.pojo.po.Admin;
import com.authority.pojo.po.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName JwtUser
 * @Description 用于进行 Security 验证的类
 * @Author guangmingdexin
 * @Date 2020/12/3 19:04
 * @Version 1.0
 **/
public class JwtUser implements UserDetails {

    private String id;

    private String username;

    private String password;

    private boolean enable;


    private Collection<? extends  GrantedAuthority> authorities;

    public JwtUser(User user, List<GrantedAuthority> roles) {
        this.id = user.getUserId();
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.enable = user.getUserState();
        this.authorities = roles;

    }

    public JwtUser(String id, String username, boolean enable, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.enable = enable;
        this.authorities = authorities;
    }

    public JwtUser() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    public String getId() {
        return id;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 会根据数据库查询
        return enable;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
