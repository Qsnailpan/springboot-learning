引入依赖
```
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
启用缓存
```
@SpringBootApplication
@EnableCaching  // 开启缓存
public class DemoEhCacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoEhCacheApplication.class, args);
	}
}
```
 `ehcache.xml`
```
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="ehcache.xsd">

	<cache name="user" 
		maxElementsInMemory="1000"
		maxEntriesLocalHeap="200" 
		timeToLiveSeconds="10">  <!-- 10s后缓存过期  -->
	</cache>
	
</ehcache>
```
项目指定配置文件（默认读取根目录 resources/  ）
```
spring:
  cache:
    ehcache:
      config: classpath:config/ehcache.xml
```
使用
```
package com.snail.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snail.jpa.UserRepository;
import com.snail.model.User;
import com.snail.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	Logger logger = Logger.getLogger(getClass());

   //  主要这里的  key = 'all' (也可使用双引号，需要 \" 转义)
	@Override
	@Cacheable(value = "user", key = "'all'")
	public List<User> findAll() {
		logger.info("findAll 没有走缓存!");
		return userRepository.findAll();
	}

	@Override
	@Cacheable(value = "user", key = "#id")
	public User findOne(Long id) {
		logger.info("findOne 没有走缓存!");
		return userRepository.findOne(id);
	}

	@Override
	@CachePut(value = "user", key = "#id")
	@CacheEvict(value = "user", key = "'all'")
	@Transactional
	public User update(Long id, User user) {
		logger.info("update  清空 key:all 缓存!");
		User entity = userRepository.findOne(id);
		entity.setFullname(user.getFullname());
		entity.setUsername(user.getUsername());
		entity.setPassword(user.getPassword());
		return entity;
	}

	@Override
	@CacheEvict(value = "user", allEntries = true, beforeInvocation = true) 
	@Transactional
	public void del(Long id) {
		logger.info("del  清空缓存!");
		userRepository.delete(id);
	}

	@Override
	@CacheEvict(value = "user", key = "'all'")
	@Transactional
	public User save(User user) {
		logger.info("save  清空 key:all 缓存!");
		return userRepository.save(user);
	}
}

```

---

#### cache 相关属性配置说明：

`diskStore`：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。
`defaultCache`：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
`name`:缓存名称。
`maxElementsInMemory`:缓存最大数目
`maxElementsOnDisk`：硬盘最大缓存个数。
`eternal`:对象是否永久有效，一但设置了，timeout将不起作用。
`overflowToDisk`:是否保存到磁盘，当系统当机时
`timeToIdleSeconds`:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
`timeToLiveSeconds`:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
`diskPersistent`：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
`memoryStoreEvictionPolicy`：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
`clearOnFlush`：内存数量最大时是否清除。
`memoryStoreEvictionPolicy`:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。