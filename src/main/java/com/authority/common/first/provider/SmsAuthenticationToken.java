package com.authority.common.first.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *
 *  短信登录 AuthenticationToken
 *
 * @ClassName SmsAuthenticationToken
 * @Author guangmingdexin
 * @Date 2021/7/5 15:48
 * @Version 1.0
 **/
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -6437322217156360297L;

    /**
     * 在 UsernamePasswordAuthenticationToken 中该字段代表登录的用户名，
     * 在这里就代表登录的手机号码
     */
    private final Object principal;

    /**
     * 校验码
     *
     */
    private final Object credit;

    // 构建一个没有鉴权的 SmsAuthenticationToken
    public SmsAuthenticationToken(Object principal, Object credit) {
        super(null);
        this.principal = principal;
        this.credit = credit;
        setAuthenticated(false);
    }

    // 构建一个有鉴权的 SmsAuthenticationToken
    public SmsAuthenticationToken(Object principal, Object credit, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credit = credit;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credit;
    }

    @Override
    public Object getPrincipal() {
        return principal;
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
