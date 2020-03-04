package com.lucasia.ginquiryfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// Enable consumption of HATEOS payloads
// @EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
// Enable Feign Clients
@EnableFeignClients
public class GinquiryFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GinquiryFrontendApplication.class, args);
	}

}
