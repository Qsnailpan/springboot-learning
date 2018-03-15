 ``` 
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v1.5.10.RELEASE)
 ```
 
 1、假如我们不想看到这个图案
 
 ```
 public static void main(String[] args) {
       SpringApplication application=new SpringApplication(Application.class);
       /**
        * OFF G关闭
        * CLOSED 后台控制台输出，默认就是这种
        * LOG 日志输出
        */
       application.setBannerMode(Banner.Mode.OFF);
       application.run(args);
    }
 
 ```
 
 或者在application.yml配置文件中配置也行
 
 ```xml
 
 spring:
  main:
    banner-mode: off
    
 ```
 
 2、如果我们想改动，那么只需要在src/main/recesources下新建一个banner 文件
 
 3、 自定义 banner图案   http://patorjk.com/software/taag
 