package com.authority.common.provider;

import com.authority.pojo.bo.JwtUser;
import com.authority.service.user.account.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName AccountAuthenticationProvider
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/5 10:22
 * @Version 1.0
 **/
@Component
public
class AccountAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDetailsChecker userDetailsChecker = new UserDetailsCheck();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        JwtUser userDetails = (JwtUser)accountUserService.loadUserByUsername(username);
        System.out.println("后台的密码： " + userDetails.getPassword());

        if(userDetails.getUsername() == null) {
            throw new BadCredentialsException("用户名，密码错误！");
        }

        userDetailsChecker.check(userDetails);

        if(username.equals(userDetails.getUsername()) &&
                passwordEncoder.encode(password).equals(userDetails.getPassword())) {
            // 用户名 密码验证成功
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return new AccountAuthenticationToken(userDetails, password, authorities);

        }else {
            throw new BadCredentialsException("用户名或者密码错误");
        }

    }

    /** providerManager会遍历所有
         * securityconfig中注册的provider集合
         * 根据此方法返回true或false来决定由哪个provider
         * 去校验请求过来的authentication
     **/

    @Override
    public boolean supports(Class<?> authentication) {
        return (AccountAuthenticationToken.class
                .isAssignableFrom(authentication));
    }


}
