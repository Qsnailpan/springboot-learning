官方文档
------
https://springcloud.cc/spring-security-zhcn.html#tech-intro-web-authentication
Github 地址
-----
https://github.com/Qsnailpan/springboot-learn/tree/master/demo-security-login

-----
Spring Security 比较复杂，尤其看资料的时候感觉挺懵的。这里基于demo一步步学习。
简介
---

#### Spring Security是什么?
Spring Security 提供了基于javaEE的企业软件全面的`安全服务`。


启动应用
-----

1、clone 代码之后，进入  目录，启动应用
```
mvn spring-boot:run
```

2、demo 演示，地址：[http://localhost:8080](http://localhost:8080) 账号 root 密码 root

-----

#### 注解 @EnableWebSecurity

```java
/**
 * 
 * @author snail  2018年1月3日
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
}


```

该注解和 `@Configuration` 注解一起使用, 注解 `WebSecurityConfigurer` 类型的类，或者利用`@EnableWebSecurity` 注解继承 `WebSecurityConfigurerAdapter`的类，这样就构成了 *Spring Security* 的配置。

#### 抽象类 WebSecurityConfigurerAdapter

`WebSecurityConfigurerAdapter` 提供了一种便利的方式去创建 `WebSecurityConfigurer`的实例，只需要重写 `WebSecurityConfigurerAdapter` 的方法，即可配置拦截什么URL、设置什么权限等安全控制。

#### 方法 configure(AuthenticationManagerBuilder auth) 、configure(HttpSecurity http)
Demo 中重写了 `WebSecurityConfigurerAdapter` 的两个方法：

```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 匹配 "/" 路径，不需要权限即可访问 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限 
	 *    authorizeRequests().antMatchers("/").permitAll()
	 * 登录地址为"/login"，登录成功默认跳转到页面 "/user"
	 * 
	 * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
	 * 
	 * 默认启用CSRF(后续研究)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll()
			.antMatchers("/user/**").hasRole("USER")
			.and().formLogin() .loginPage("/login").defaultSuccessUrl("/user")
			.and().logout().logoutUrl("/logout") .logoutSuccessUrl("/login");
	}

	/**
	 * 在内存中创建一个名为 "root" 的用户，密码为 "root"，拥有 "USER" 权限
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication().withUser("root").password("root").roles("USER");
	}

}
```
#### 类 AuthenticationManagerBuilder


`AuthenticationManagerBuilder` 用于创建一个 `AuthenticationManager`，让我们能够轻松的实现内存验证、LADP验证、基于JDBC的验证、添加`UserDetailsService`、添加`AuthenticationProvider`

----

默认配置
```
	/**
	 * 复写这个方法来配置 {@link HttpSecurity}. 
	 * 通常，子类不能通过调用 super 来调用此方法，因为它可能会覆盖其配置。 默认配置为：
	 * 
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 *
	 */
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
	}
```

#### final 类 HttpSecurity
表1 HttpSecurity 常用方法及说明

| 方法 | 说明 |
| ---- | ---- |
| `openidLogin()` | 用于基于 OpenId 的验证 |
| `headers()`| 将安全标头添加到响应 |
| `cors()` | 配置跨域资源共享（ CORS ） |
| `sessionManagement()` | 允许配置会话管理 |
| `portMapper()` | 允许配置一个`PortMapper`(`HttpSecurity#(getSharedObject(class))`)，其他提供`SecurityConfigurer`的对象使用 `PortMapper` 从 HTTP 重定向到 HTTPS 或者从 HTTPS 重定向到 HTTP。默认情况下，Spring Security使用一个`PortMapperImpl`映射 HTTP 端口8080到 HTTPS 端口8443，HTTP 端口80到 HTTPS 端口443 |
| `jee()` | 配置基于容器的预认证。 在这种情况下，认证由Servlet容器管理 |
| `x509()` | 配置基于x509的认证 |
| `rememberMe` | 允许配置“记住我”的验证 |
| `authorizeRequests()` | 允许基于使用`HttpServletRequest`限制访问 | 
| `requestCache()` | 允许配置请求缓存 |
| `exceptionHandling()` | 允许配置错误处理 |
| `securityContext()` |  在`HttpServletRequests`之间的`SecurityContextHolder`上设置`SecurityContext`的管理。 当使用`WebSecurityConfigurerAdapter`时，这将自动应用 | 
| `servletApi()` | 将`HttpServletRequest`方法与在其上找到的值集成到`SecurityContext`中。 当使用`WebSecurityConfigurerAdapter`时，这将自动应用 |
| `csrf()` | 添加 CSRF 支持，使用`WebSecurityConfigurerAdapter`时，默认启用 |
| `logout()` | 添加退出登录支持。当使用`WebSecurityConfigurerAdapter`时，这将自动应用。默认情况是，访问URL"/ logout"，使HTTP Session无效来清除用户，清除已配置的任何`#rememberMe()`身份验证，清除`SecurityContextHolder`，然后重定向到"/login?success" |
| `anonymous()` | 允许配置匿名用户的表示方法。 当与`WebSecurityConfigurerAdapter`结合使用时，这将自动应用。 默认情况下，匿名用户将使用`org.springframework.security.authentication.AnonymousAuthenticationToken`表示，并包含角色 "ROLE_ANONYMOUS" |
| `formLogin()` | 指定支持基于表单的身份验证。如果未指定`FormLoginConfigurer#loginPage(String)`，则将生成默认登录页面 |
| `oauth2Login()` | 根据外部OAuth 2.0或OpenID Connect 1.0提供程序配置身份验证 |
| `requiresChannel()` | 配置通道安全。为了使该配置有用，必须提供至少一个到所需信道的映射 |
| `httpBasic()` | 配置 Http Basic 验证 |
| `addFilterAt()`  | 在指定的Filter类的位置添加过滤器 |


---

 