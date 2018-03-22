demo-redis  
 - mybatis
 - mysql
 - 包含 全注解版 / (xml 配置版)

sql 

```
DROP TABLE IF EXISTS  `city`;
CREATE TABLE `city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市编号',
  `province_id` int(10) unsigned NOT NULL COMMENT '省份编号',
  `city_name` varchar(25) DEFAULT NULL COMMENT '城市名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT city VALUES (1 ,510100,'成都市','一座来了就不想离开的城市');
```

启动 redis 

```
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 4.0.8 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                   
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 17344
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           http://redis.io        
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                                  
  `-._    `-._`-.__.-'_.-'    _.-'                                   
      `-._    `-.__.-'    _.-'                                       
          `-._        _.-'                                           
              `-.__.-'          
```

启动工程

测试工具  Postman

1. 访问 get http://127.0.0.1:8080/api/city/1
```
插入缓存 >> City{id=1, provinceId=510100, cityName='成都市', description='一座来了就不想离开的城市'}
```
再次访问   (10s 过期)
 ```
 从缓存中获取了key >> City{id=1, provinceId=510100, cityName='成都市', description='一座来了就不想离开的城市'}
 ```
 
 主要代码实现
 
 ```
 public City findCityById(Long id) {
		// 从缓存中获取城市信息
		String key = "city_" + id;
		ValueOperations<String, City> operations = redisTemplate.opsForValue();
		// 缓存存在
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			City city = operations.get(key);
			LOGGER.info(" 从缓存中获取了key >> " + city.toString());
			return city;
		}
		// 从 DB 中获取城市信息
		City city = cityDao.findById(id);
		// 插入缓存 10s 过期
		operations.set(key, city, 10, TimeUnit.SECONDS);
		LOGGER.info(" 插入缓存 >> " + city.toString());
		return city;
	}
 
 ```
 
 2. 相应的更新和删除功能, 会去找有没缓存,有缓存会删掉