package com.authority.common.first.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName AccountAuthenticationToken
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/5 10:14
 * @Version 1.0
 **/
public class AccountAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -6437322217156360297L;

    // 代表用户名
    private final Object principal;
    // 代表密码
    private Object credentials;


    public AccountAuthenticationToken(Object principal, Object credentials) {
        super(null);

        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public AccountAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;

        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException{
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
