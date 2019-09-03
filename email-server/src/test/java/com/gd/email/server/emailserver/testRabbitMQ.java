package com.gd.email.server.emailserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@Component
@RabbitListener(queues ="register.*")
public class testRabbitMQ {

    @RabbitHandler
    public void handler(String obj){
        System.out.println("hello: "+obj);
    }
}