package com.snail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证失败时 处理
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf ().disable ()
                .authorizeRequests ()
                .antMatchers ("/", "/home", "/about").permitAll ()
                .antMatchers ("/admin/**").hasAnyRole ("ADMIN")
                .antMatchers ("/user/**").hasAnyRole ("USER")
                .anyRequest ().authenticated ()
                .and ()
                .formLogin ()
                .loginPage ("/login")
                .permitAll ()
                .and ()
                .logout ()
                .permitAll ()
                .and ()
                .exceptionHandling ().accessDeniedHandler (accessDeniedHandler);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication ()
                .withUser ("user").password ("root").roles ("USER")
                .and ()
                .withUser ("admin").password ("root").roles ("ADMIN");
    }


  /*  @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring ()
                .antMatchers ("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
   */
}
