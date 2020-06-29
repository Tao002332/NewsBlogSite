package com.newsblogsite.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
public class CommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class);
    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return  new JwtUtil();
    }
}
