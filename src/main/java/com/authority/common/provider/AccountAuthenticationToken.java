package com.authority.common.provider;

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

    private final Object principal;
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

        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
