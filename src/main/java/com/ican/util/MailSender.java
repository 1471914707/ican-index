package com.ican.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public boolean send(String to, String content) {
        try {
            String nick = null;
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.163.com");
            mailSender.setPort(465);
            mailSender.setUsername("18813960106@163.com");
            mailSender.setPassword("12ab12ab");
            //加认证机制
            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.auth", true);
            javaMailProperties.put("mail.smtp.starttls.enable", true);
            javaMailProperties.put("mail.smtp.timeout", 5000);
            javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            mailSender.setJavaMailProperties(javaMailProperties);
            //创建邮件内容
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("18813960106@163.com");
            message.setTo(to);
            message.setSubject("毕业设计平台ICAN消息");
            message.setText(content);
            //发送邮件
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }

    public static void mainx(String[] args) {
        String nick = null;

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.163.com");
            mailSender.setPort(465);
            mailSender.setUsername("18813960106@163.com");
            mailSender.setPassword("12ab12ab");
            //加认证机制
            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.auth", true);
            javaMailProperties.put("mail.smtp.starttls.enable", true);
            javaMailProperties.put("mail.smtp.timeout", 5000);
            javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            mailSender.setJavaMailProperties(javaMailProperties);
            //创建邮件内容
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("18813960106@163.com");
            message.setTo("1471914707@qq.com");
            message.setSubject("早上好");
            message.setText("这是主要的内容");
            //发送邮件
            mailSender.send(message);
            /*nick = MimeUtility.encodeText("Ican毕业设计平台");
            InternetAddress from = new InternetAddress(nick + "<admin@ican.com>");
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            String result = "欢迎登陆！";
            mimeMessageHelper.setTo("1471914707@qq.com");
            mimeMessageHelper.setFrom("admin@ican.com");
            mimeMessageHelper.setSubject("大家好");
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);*/

    }

    /*@Autowired
    private VelocityEngine velocityEngine;

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("牛客中级课");
            InternetAddress from = new InternetAddress(nick + "<course@nowcoder.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }
*/
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("admin@ican.com");
        mailSender.setPassword("1212abab");
        mailSender.setHost("smtp.163.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
