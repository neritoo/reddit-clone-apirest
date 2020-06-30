package com.gavilan.redditapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditApirestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditApirestApplication.class, args);
    }

}
