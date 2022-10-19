package com.example.springbootjpa;

import com.example.springbootjpa.common.AuditorAwearImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwear")
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class SpringBootJpaApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaApplication.class, args);
    }

}
