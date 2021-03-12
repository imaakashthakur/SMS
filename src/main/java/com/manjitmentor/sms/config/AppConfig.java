package com.manjitmentor.sms.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//This tells Spring Boot that this class is a source of Bean Definition
@Configuration
@Slf4j

public class AppConfig {
    //If no name is defined in Bean annotation, it takes method name as default
    @Bean
    public ModelMapper modelMapper(){
        log.info("Triggered ModelMapper Bean!");
        return new ModelMapper();
    }
}
