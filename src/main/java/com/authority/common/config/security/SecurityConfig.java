package com.authority.common.config.security;


import com.authority.common.exception.AccessDenied;
import com.authority.common.filter.JWTAccountAuthenticationFilter;
import com.authority.common.filter.JWTAuthenticationFilter;
import com.authority.common.filter.JWTAuthorizationFilter;
import com.authority.common.provider.AccountAuthenticationProvider;
import com.authority.common.utils.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 光明的心
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService adminUserService;

    @Autowired
    private AccountAuthenticationProvider provider;

    @Autowired
    private AccessDenied accessDenied;
    // 密码加密
    @Bean
    PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserService);
        auth.authenticationProvider(provider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 可以访问用户登陆接口
                .antMatchers("/admins/login")
                .permitAll()
                .antMatchers("/account/login")
                .permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(metadataSource());
                        o.setAccessDecisionManager(manager());
                        return o;
                    }
                })
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilterBefore(new JWTAccountAuthenticationFilter(authenticationManager()), JWTAuthenticationFilter.class)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDenied);

    }


    @Bean
    public FilterMetadataSource metadataSource() {
        return new FilterMetadataSource();
    }

    @Bean
    public MyAccessDesicionManager manager() {
        return new MyAccessDesicionManager();
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.getWriter().write(new ObjectMapper().writeValueAsString(Msg.setResult("403", null, "权限不足")));
        };
    }
}

