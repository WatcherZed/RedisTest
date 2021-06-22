package com.lyk.redisdemo;

import com.lyk.redisdemo.controller.NoticeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedisdemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedisdemoApplication.class, args);
        NoticeController bean = run.getBean(NoticeController.class);
//        bean.jinhuo();
        bean.buyProduct();
    }

}
