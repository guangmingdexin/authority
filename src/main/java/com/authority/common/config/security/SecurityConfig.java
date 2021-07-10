package com.authority.common.config.security;



import com.authority.common.exception.AccessDenied;
import com.authority.common.first.filter.JWTAccountAuthenticationFilter;
import com.authority.common.first.filter.JWTAuthenticationFilter;
import com.authority.common.first.filter.JWTAuthorizationFilter;
import com.authority.common.first.filter.JWTSmsAuthenticationFilter;
import com.authority.common.first.provider.AccountAuthenticationProvider;
import com.authority.common.first.provider.SmsAuthenticationProvider;
import com.authority.common.utils.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 这种写法有点问题，只能单个 Filter 生效
 *
 * @author 光明的心
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDenied accessDenied;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Resource
    private UserDetailsService adminUserService;

    @Autowired
    private AccountAuthenticationProvider accountAuthenticationProvider;

    @Autowired
    private SmsAuthenticationProvider smsAuthenticationProvider;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager manager;


    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myAuthenticationDetailsSource
            = new WebAuthenticationDetailsSource();


    // 密码加密
    @Bean
    PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(adminUserService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
        auth.authenticationProvider(accountAuthenticationProvider);
        auth.authenticationProvider(smsAuthenticationProvider);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
        filter.setAuthenticationDetailsSource(myAuthenticationDetailsSource);
        filter.setAuthenticationManager(manager);
        return filter;
    }

    @Bean public JWTAccountAuthenticationFilter jwtAccountAuthenticationFilter() {
        JWTAccountAuthenticationFilter filter = new JWTAccountAuthenticationFilter();
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationDetailsSource(myAuthenticationDetailsSource);

        return filter;
    }

    @Bean
    public JWTSmsAuthenticationFilter jwtSmsAuthenticationFilter() {
        JWTSmsAuthenticationFilter filter = new JWTSmsAuthenticationFilter();
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationDetailsSource(myAuthenticationDetailsSource);

        return filter;
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        JWTAuthorizationFilter filter = new JWTAuthorizationFilter(manager);

        filter.setAuthenticationDetailsSource(myAuthenticationDetailsSource);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                // 可以访问用户登陆接口
                .antMatchers("/admins/login", "/sms/login", "/account/login", "/vercode", "/hello")
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
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(jwtAccountAuthenticationFilter(), JWTAuthenticationFilter.class)
                .addFilterBefore(jwtSmsAuthenticationFilter(), JWTAccountAuthenticationFilter.class)
                .addFilter(jwtAuthorizationFilter())
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
            if(e instanceof UsernameNotFoundException) {
                response.getWriter().write(new ObjectMapper().writeValueAsString(Msg.setResult("304", null, e.getMessage())));
            }else {
                System.out.println("config-e : " + e);
                response.getWriter().write(new ObjectMapper().writeValueAsString(Msg.setResult("403", null, e.getMessage())));
            }

        };
    }
}

