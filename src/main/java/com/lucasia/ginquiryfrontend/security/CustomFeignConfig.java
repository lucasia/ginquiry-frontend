package com.lucasia.ginquiryfrontend.security;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

public class CustomFeignConfig {

/*
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }
*/
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("guest", "guest");
    }


}