    
### ehcache

@(demo)[jpa| hibernate | ehcache]   

`author: snail`

实例基于 [demo-jpa](https://github.com/Qsnailpan/springboot-learn) 编写

---

### 介绍
EhCache 是一个纯Java的进程内缓存框架，具有快速、精干等特点，是Hibernate中默认的CacheProvider。
#### 特点
1. 快速
2. 简单
3. 多种缓存策略
4. 缓存数据有两级：内存和磁盘，因此无需担心容量问题
5. 缓存数据会在虚拟机重启的过程中写入磁盘
6. 可以通过RMI、可插入API等方式进行分布式缓存
7. 具有缓存和缓存管理器的侦听接口
8. 支持多缓存管理器实例，以及一个实例的多个缓存区域
9. 提供Hibernate的缓存实现
### 使用

#### 引入依赖
```xml
<!--开启 cache 缓存 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<!-- ehcache 缓存 -->
<dependency>
	<groupId>net.sf.ehcache</groupId>
	<artifactId>ehcache</artifactId>
</dependency>

```

#### 缓存 配置   `ehcache.xml`
```xml
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="ehcache.xsd">

	<cache name="user" 
	maxElementsInMemory="1000" 
	maxEntriesLocalHeap="200"  
	timeToLiveSeconds="10">  <!-- 10s后缓存过期  -->
	</cache>

</ehcache>
```

实现类接口  `UserServiceImpl.java`
```java
@Service
@CacheConfig(cacheNames = "user")   // 使用ehcache.xml 中 user 缓存策略 
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	Logger logger = Logger.getLogger(getClass());

	@Override
	@Cacheable // 加入缓存
	public List<User> findAll() {
		logger.info("没有走缓存!");
		return userRepository.findAll();
	}
}

```

编写一个 `UserController.java`
```java
@RestController
@RequestMapping(value="/users")     
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @RequestMapping(method=RequestMethod.GET)
    public List<User> getUserList() {
        return userService.findAll();
    }
}
```

#### 开启缓存注解    `@EnableCaching `

```java
@SpringBootApplication
@EnableCaching  // 开启缓存注解
public class DemoEhCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEhCacheApplication.class, args);
	}
}
```

访问 http://localhost:8080/users    每10s才会请求一次数据库

```xml
2018-01-05 17:28:52.897  INFO 1796 --- [nio-8090-exec-7] com.snail.service.impl.UserServiceImpl   : 没有走缓存!
Hibernate: select user0_.id as id1_0_, user0_.fullname_ as fullname2_0_, user0_.password_ as password3_0_, user0_.username_ as username4_0_ from bf_user_ user0_
```

#### 更多属性配置

```vim
diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。

defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。

name:缓存名称。

maxElementsInMemory:缓存最大数目

maxElementsOnDisk：硬盘最大缓存个数。

eternal:对象是否永久有效，一但设置了，timeout将不起作用。

overflowToDisk:是否保存到磁盘，当系统当机时

timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。

timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。

diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。

diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。

memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。

clearOnFlush：内存数量最大时是否清除。

memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。

```



----

Github:https://github.com/Qsnailpan/springboot-learn
官方文档：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/