### springboot 定制个性 banner

@(boot)[banner]   

`author: snail`

#### 什么是banner?
``` vim
 .   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
 '  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::       (v1.5.10.RELEASE)
```
这是Spring Boot 项目启动时  默认的banner。

#### 个性定制
 在resource/  新建banner 文件。 如banner.txt、banner.jpg ...
自定义 banner图案网站： http://patorjk.com/software/taag , 然后将ASCII字符画复制进去

如：

`banner.txt`
``` vim
${AnsiColor.BRIGHT_RED}：设置控制台中输出内容的颜色
${application.version}：用来获取MANIFEST.MF文件中的版本号
${application.formatted-version}：格式化后的${application.version}版本信息
${spring-boot.version}：Spring Boot的版本号
${spring-boot.formatted-version}：格式化后的${spring-boot.version}版本信息
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//            佛祖保佑       永不宕机     永无BUG                  //
////////////////////////////////////////////////////////////////////

```

#### 关闭banner
```
SpringApplication application = new SpringApplication(DemoBannerApplication.class);
		// 关闭banner
		application.setBannerMode(Mode.OFF);
		application.run(args);
```

---
Spring Boot 默认寻找 Banner 的顺序是:

依次在 Classpath 下找 文件 banner.gif , banner.jpg , 和 banner.png , 先找到谁就用谁。
继续 Classpath 下找 banner.txt
上面都没有找到的话, 用默认的 SpringBootBanner 。

---

