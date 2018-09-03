    
### Interceptor 拦截器

@(demo)[web| Interceptor ]   

`author: snail`

实例基于 [demo](https://github.com/Qsnailpan/springboot-learn) 编写

---

### 介绍
依赖于web框架，在SpringMVC中就是依赖于SpringMVC框架。在实现上基于Java的反射机制，属于面向切面编程（AOP）的一种运用。

```
HttpRequest ----> DispactherServlet ----> HandlerInterceptor ---->Controller----> HandlerInterceptor ----> HttpResponse
```
#### 特点
①拦截器interceptor是基于Java的反射机制的，而过滤器Filter是基于函数回调,实现的filter接口中doFilter方法就是回调函数。
②拦截器interceptor不依赖与servlet容器，过滤器Filter依赖与servlet容器,没有servlet容器就无法来回调doFilter方法。
③拦截器interceptor只能对action请求起作用，而过滤器Filter则可以对几乎所有的请求起作用,Filter的过滤范围比Interceptor大,Filter除了过滤请求外通过通配符可以保护页面，图片，文件等等。
④拦截器interceptor可以访问action上下文、值栈里的对象，而过滤器Filter不能访问。
⑤在action的生命周期中，拦截器interceptor可以多次被调用，而过滤器Filter只能在容器初始化时被调用一次。
⑥拦截器interceptor可以获取IOC容器中的各个bean，而过滤器Filter就不行，这点很重要，在拦截器里注入一个service，可以调用业务逻辑。
### 使用

#### 引入依赖
```xml
<!-- web -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>

```

主要实现类  `MvcConfig.java`

```java

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	WebInterceptor webInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 指定拦截的url */
		registry.addInterceptor(webInterceptor).addPathPatterns("/**");
	}
}

```

编写一个 `WebInterceptor.java`

```java
@Component
public class WebInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(WebInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle -在请求处理之前进行调用（Controller方法调用之前）");
		
		Boolean flag = true;

		// ... 逻辑
		if ( ) {   // 进入 Controller
			return true;
		} else {  // 终止
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		logger.info("postHandle -请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("postHandle -在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");

	}
}	
```


----
