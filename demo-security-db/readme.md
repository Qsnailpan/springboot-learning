前言
-----
这篇推文主要记录实现 springsecurity 基于数据库验证（非加密）过程和 遇到的坑

使用 themeleaf + mysql + hibernate（JPA）

Github 地址
---
https://github.com/Qsnailpan/springboot-learn/tree/master/demo-security-login-db

启动应用
-----

1、clone 代码之后，更改数据库配置，进入目录，启动应用
```
mvn spring-boot:run
```
新增注册页面，注册成功的用户本地后台代码默认给的 USER 权限
![Alt text](./屏幕快照 2018-01-21 13.17.34.png)


```java

@Configuration
public class InitDataConfig {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	/**
	 * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，
	 * 
	 * 类似于Serclet的init()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
	 * 
	 */
	@PostConstruct
	public void init() {
		// 初始用户信息
		if (userRepository.count() < 1) {
			Role role1 = new Role(null, "ROLE_USER", "用户");
			Role role2 = new Role(null, "ROLE_ADMIN", "管理员");

			roleRepository.save(role1);
			roleRepository.save(role2);

			List<Role> roles = new ArrayList<Role>();
			User user = new User();
			user.setUsername("root");
			user.setFullname("张三");
			user.setMobile("12345-");
			user.setPassword("root");

			roles.add (role2);
			user.setRoles(roles);

			userRepository.save(user);
		}
	}
}
```

2、demo 演示，地址：[http://localhost:8080](http://localhost:8080) 新注册的用户能够进入到个人中心

- 初始化的root 不能访问  /user  因为没有ROLE_USER 权限
![Alt text](./屏幕快照 2018-01-21 13.30.03.png)


-----

#### WebSecurityConfig

```java

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

// ----------------------以上是新增的代码-------------------------
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
                .and ().formLogin ().loginPage ("/login").defaultSuccessUrl ("/user")
                .and ().logout ().logoutUrl ("/logout").logoutSuccessUrl ("/login");
    }

}

```
#### CustomUserService
```java 
/**
 * 本类需要实现 org.springframework.security.core.userdetails.UserDetailsService接口
 * 
 * 然后覆盖重写 loadUserByUsername(String userName)方法
 * 在该方法内部，需要添加 userName,passWord,权限集合,其他参数 到我们 SecurityUser
 */
public class CustomUserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger (CustomUserService.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info ("收到用户名：" + username);
        User user = userRepository.findByUsername (username);
        if (user == null) {
            throw new UsernameNotFoundException ("username 不存在");
        }

        return new SecurityUser (user.getUsername (), user.getPassword (), createGrantedAuthority (user.getRoles ()));
    }

    // 装载用户权限 信息
    List<GrantedAuthority> createGrantedAuthority(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority> ();

        for (Role role : roles) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority (role.getName ());// 权限实体
            authorities.add (grantedAuthority); // 增加到权限队列中
        }
        return authorities;
    }
}



```
#### SecurityUser
```java
package com.snail.security.bean;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *   注意
 *
 *   这里继承的是 org.springframework.security.core.userdetails.User;
 */
public class SecurityUser extends User {
	private static final long serialVersionUID = -254576396255401176L;
	//  可以附加其他信息 
	// private String mail

	/**
	 * @param username   用户名
	 * @param password   密码
	 * @param authorities  拥有权限信息
	 */
	
	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}

``` 

springsecurity 基于数据库验证大致就这样。在理一下
-  重写`configure(AuthenticationManagerBuilder auth) ` 自己去装载用户信息
```java

@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**对于数据库密码不加密的情况*/
        auth.userDetailsService (customUserService ());
    }
```

- 在我们实现`UserDetailsService`接口的类中重写`UserDetails loadUserByUsername(String username) throws UsernameNotFoundException ` 方法 ， 返回一个实现`UserDetails`接口的实体类 , 看源码可以知道 org.springframework.security.core.userdetails.User  是实现这个接口的
```java

public class CustomUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// org.springframework.security.core.userdetails.User
        return new User (用户名, 密码, 用户权限);
    }
```

-----

遇到的坑
-----

####  用户权限匹配

最开始这样写的代码 （错误实例❌）
`WebSecurityConfig`
```  
// 访问/user/** 下面的资源需要 USER 权限（角色）
.antMatchers ("/user/**").hasRole ("USER")
```
`InitDataConfig`
``` 
// 在项目启动时初始化数据
Role role1 = new Role(null, "USER", "用户");
roleRepository.save(role1);
```

这样没毛病嘛,需要USER角色 就在数据库加一个USER角色 , 再跟用户关联起来

> 这样死活匹配不了权限信息

解决方案一 （猜）
```
  // 改变为 ROLE_USER
.antMatchers ("/user/**").hasRole ("ROLE_USER")
```
启动项目 - `报错`

```xml
Caused by: java.lang.IllegalArgumentException: role should not start with 'ROLE_' since it is automatically inserted. Got 'ROLE_USER'
```
role 不能是ROLE_ 开头 ， ROLE_ 是自动插入的。

方案二 （还是猜）

还原
```  
// 访问/user/** 下面的资源需要 USER 权限（角色）
.antMatchers ("/user/**").hasRole ("USER")
```
`InitDataConfig`  数据库储存以 `ROLE_ ` 开头
``` 
// 在项目启动时初始化数据
Role role1 = new Role(null, "ROLE_USER", "用户");
roleRepository.save(role1);
```

项目启动测试    ------  成功！
----

分析：

   `  hasRole ("USER")`  springsecurity 自动插入了`ROLE_`前缀最终是拿`ROLE_USER`和数据库里面我们存的是 `USER` 匹配
     

---

####  登录表单密码识别
我们并没有做密码校验，登录之后springsecurity 自动获取表单密码和数据库查询到的用户密码匹配。


现在代码正确实例
`login.html`

```html
<input type="text" class="form-control" name="username"
id="username" placeholder="账号"/>

<input type="password" class="form-control" name="password" id="password" placeholder="密码"/>

```
> 这里的name = "username"  name = "password" 是springsecurity 默认读取的字段，也就是说这样就能识别你输入的是用户名还是密码，点击登录后就会取name = "password" 表单的值 和数据库进行校验

当然也可以更改这个字段（默认挺好的哈，没必要改）

`WebSecurityConfig`
``` 
 .and ().formLogin ().loginPage ("/login")
 // 这里和登录表单`name`属性一致就行
 .usernameParameter ("username")  
 .passwordParameter ("password")
```

