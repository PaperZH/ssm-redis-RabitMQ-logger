package com.gd.email.server.emailserver.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;


class SendEmailUtils {
    public static final String DEFAULT_ENCODING ="UTF-8";
    private static final Logger log= LoggerFactory.getLogger(SendEmailUtils.class);


    public static void sendTextWithHtml(JavaMailSenderImpl sender, String[] tos, String subject, String text) throws
            Exception{
        MimeMessage mailMessage = sender.createMimeMessage();
        initMimeMessageHelper(mailMessage,tos,sender.getUsername(),subject,text);
        sender.send(mailMessage);
        log.debug("发送带有html邮件；");
    }

    public static void sendTextWithImg(JavaMailSenderImpl sender, String[] tos, String subject, String text,
                                       String imgId,String imgPath)
            throws
            MessagingException{
            MimeMessage mailMessage = sender.createMimeMessage();
            MimeMessageHelper messageHelper = initMimeMessageHelper(mailMessage,tos,sender.getUsername(),subject,
                    text,true,true,"GBK");
            FileSystemResource img = new FileSystemResource(new File(imgPath));
            messageHelper.addInline(imgId, img);
            // 发送邮件
            sender.send(mailMessage);

            log.info("邮件发送成功..");
    }

    private static MimeMessageHelper initMimeMessageHelper(MimeMessage mailMessage, String[] tos, String from, String
            subject, String text) throws MessagingException {
        return initMimeMessageHelper(mailMessage,tos,from,subject,text,true,false,DEFAULT_ENCODING);
    }

    private static MimeMessageHelper initMimeMessageHelper(MimeMessage mailMessage, String[] tos, String from, String
            subject, String text, boolean isHtml, boolean multipart, String encoding) throws MessagingException{
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,multipart,encoding);
        messageHelper.setTo(tos);
        messageHelper.setFrom(from);
        messageHelper.setSubject(subject);

        messageHelper.setText(text,isHtml);
        return messageHelper;
    }

    public static JavaMailSenderImpl initJavaMailSender() {

        Properties properties = new Properties();
        properties.setProperty("mail.debug", "true");// 是否显示调试信息(可选)
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.auth", "true");
        properties.put(" mail.smtp.timeout ", " 25000 ");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setHost("smtp.163.com");
        javaMailSender.setUsername("15671562556@163.com"); // 根据自己的情况,设置username
        javaMailSender.setPassword("code1314"); // 根据自己的情况, 设置password
        javaMailSender.setPort(465);
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }


    public static void main(String[] args) throws Exception {

        JavaMailSenderImpl sender = initJavaMailSender();
        String[] ss = { "1374252753@qq.com", "ucar.code.player@tom.com" };

        sendTextWithHtml(sender, ss, "测试简单文本邮件发送！ ", " <a href='http://work.dev.gomeplus.com/'>test</a>测试我的简单邮件发送机制！！ ");

    }

}
