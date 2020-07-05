package com.gavilan.redditapirest;

import com.gavilan.redditapirest.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditApirestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditApirestApplication.class, args);
    }

}
