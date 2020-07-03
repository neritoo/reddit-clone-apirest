package com.gavilan.redditapirest.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author: Eze Gavilán
 **/

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init(){

        TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Cordoba"));
    }
}
