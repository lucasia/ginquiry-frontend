package com.lucasia.ginquiryfrontend;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@Log4j2
class GinquiryFrontendApplicationTests {

	@Value("${ginquiry.brands.endpoint}")
	private String brandEndpoint;

	@Autowired
	Environment environment;

	@Test
	void contextLoads() {
	}


	@Test
	void findProperty() {

		log.info(brandEndpoint);
		Assertions.assertNotNull(brandEndpoint);

		final String endpointProperty = environment.getProperty("ginquiry.brands.endpoint");
		Assertions.assertNotNull(endpointProperty);

	}

}
