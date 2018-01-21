package com.snail.security;

import com.snail.security.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 我们自己的类    去装载用户、权限信息加载到内存
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService ();
    }

    /**
     * 用户登陆校验
     * 调用了customUserService()，内部覆盖重写了 UserDetailsService.loadUserByUsername,
     * 需返回配置了权限的UserDetails的子类对象作为登陆用户权限配置的依据
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**对于数据库密码不加密的情况*/
        auth.userDetailsService (customUserService ());
    }


    /**
     * 匹配 "/" 路径，不需要权限即可访问 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * authorizeRequests().antMatchers("/").permitAll()
     * 登录地址为"/login"，登录成功默认跳转到页面 "/user"
     * <p>
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * <p>
     * 默认启用CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests ().antMatchers ("/").permitAll ()
                .antMatchers ("/user/**").hasRole ("USER")
                .and ().formLogin ().loginPage ("/login").usernameParameter ("username").passwordParameter ("password").defaultSuccessUrl ("/user")
                .and ().logout ().logoutUrl ("/logout").logoutSuccessUrl ("/login");
    }

}
