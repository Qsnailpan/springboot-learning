package com.snail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by snail on 2017/5/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    // 邮件发送给谁
    final String mailTo = "1621459725@qq.com";

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail (mailTo, "test simple mail", " hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail (mailTo, "test simple mail", content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath = "/Users/lipan/Desktop/image/1.jpg";
        mailService.sendAttachmentsMail (mailTo, "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    @Test
    public void sendInlineResourceMail() {
        String rscId = "006";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "/Users/lipan/Desktop/image/1.jpg";

        mailService.sendInlineResourceMail (mailTo, "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context ();
//        context.setVariable("id", "0006");
        String emailContent = templateEngine.process ("emailTemplate", context);
        mailService.sendHtmlMail (mailTo, "主题：这是模板邮件", emailContent);
    }
}
