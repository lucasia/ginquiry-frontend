package com.lucasia.ginquiryfrontend;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerSecurityConfiguration {

    @Value("${spring.security.user.name}")
    private String username;

    @Value(("${spring.security.user.password}"))
    private String password;


    /*
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }

     */
}
