package com.newsblogsite.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/
@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
