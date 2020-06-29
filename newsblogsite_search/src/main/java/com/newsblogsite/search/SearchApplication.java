package com.newsblogsite.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/
@SpringBootApplication
@EnableEurekaClient
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class);
    }
}
