package com.demo.activiti.springactiviti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class,org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class SpringActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringActivitiApplication.class, args);
    }

}
