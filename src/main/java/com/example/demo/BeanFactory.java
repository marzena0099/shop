package com.example.demo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration("dupa blada")
public class BeanFactory {
    int i;

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    @Lazy
    String abc() {
        System.out.println("wywoluje abc");
        return String.valueOf(i++);
    }

}
