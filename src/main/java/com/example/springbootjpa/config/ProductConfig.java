package com.example.springbootjpa.config;

import com.example.springbootjpa.common.AuditorAwearImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ProductConfig {
    @Bean
    public AuditorAware<String> auditorAwear(){
        return new AuditorAwearImpl();
    }
}
