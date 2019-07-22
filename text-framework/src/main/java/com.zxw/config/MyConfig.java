package com.zxw.config;

import com.zxw.pojo.User;
import org.smart4j.framework.context.annotation.Bean;
import org.smart4j.framework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public User user() {
        User user = new User();
        return user;
    }
}
