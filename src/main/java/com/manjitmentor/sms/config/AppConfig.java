package com.manjitmentor.sms.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration              //This tells Spring Boot that this class is a source of Bean Definition
@Slf4j

public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        log.info("Triggered ModelMapper Bean!");
        return new ModelMapper();
    }
}
