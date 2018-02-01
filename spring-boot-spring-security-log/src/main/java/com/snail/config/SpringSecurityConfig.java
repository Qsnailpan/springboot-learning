package com.snail.config;

import com.snail.security.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证失败时 处理
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    LogoutHandler logoutHandler;

    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService ();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder ();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf ().disable ().authorizeRequests ()
                .antMatchers ("/", "/home", "/about", "/static/**", "/webjars/**").permitAll ()
                .antMatchers ("/admin/**").hasAnyRole ("ADMIN")
                .antMatchers ("/user/**").hasAnyRole ("USER").anyRequest ().authenticated ()

                .and ().formLogin ().loginPage ("/login").successHandler (authenticationSuccessHandler).permitAll ()
                .and ().logout ().addLogoutHandler (logoutHandler).permitAll ()
                .and ().exceptionHandling ().accessDeniedHandler (accessDeniedHandler);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService (customUserService ()).passwordEncoder (bCryptPasswordEncoder ());
    }
}
