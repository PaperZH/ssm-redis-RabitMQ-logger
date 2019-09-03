package com.gd.email.server.emailserver.Util;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import static com.gd.email.server.emailserver.Util.SendEmailUtils.initJavaMailSender;

@Component
@RabbitListener(queues ="register.*")
public class ReceiveMQ {

    private static final org.slf4j.Logger log= LoggerFactory.getLogger(ReceiveMQ.class);
    @RabbitHandler
    public void handler(String obj) throws Exception {
        JavaMailSenderImpl sender = initJavaMailSender();
        String[] emails;
        String uuid;
        String name;
        log.info("send email");
        String[] message = obj.split(".");
        if(message!=null){
             uuid = message[0];
             emails = new String[]{message[1]};
             name = message[2];
            new SendEmailUtils().sendTextWithHtml(sender, emails, "ucar用户注册邮件", "用户："+name+",您好！<br>" +"    " +
                    "您现在是在验证注册ucar用户注册信息。确认注册请点击"+
                    "<a href='http://127.0.0.1:8080/registerConfirm/"+uuid+"'>注册</a>,或者直访问 " +
                    "http://127.0.0.1:8080/registerConfirm/"+uuid);
        }
    }
}
