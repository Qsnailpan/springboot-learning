## 使用 spring-boot-mail  发送邮件

包含
- 文本邮件
- html邮件
- 附件邮件
- 图片邮件
- 模板邮件

主要依赖配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
 </dependency>

```

> 以 QQ 、163邮箱为例

注意事项：

```xml
# 邮件服务器地址 （qq 邮箱为例）
spring.mail.host=smtp.qq.com
# 登录邮箱用户名
spring.mail.username=132@qq.com
# 登录密码 （16位授权码）
spring.mail.password=***
# 使用SMTPS协议465端口
spring.mail.port=465
# SSL证书Socket工厂
spring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
# 邮箱用户名（发送方）
mail.fromMail.addr=132@qq.com
spring.mail.properties.mail.debug=true
spring.mail.default-encoding=UTF-8

```

简单邮件发送实现代码

```java
@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage ();
        message.setFrom (from);
        message.setTo (to);
        message.setSubject (subject);
        message.setText (content);

        try {
            mailSender.send (message);
            logger.info ("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error ("发送简单邮件时发生异常！", e);
        }

    }
}

```

测试类

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    // 邮件发送给谁
    final String mailTo = "***@**.com";

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail (mailTo, "test simple mail", " hello this is simple mail");
    }
}

```