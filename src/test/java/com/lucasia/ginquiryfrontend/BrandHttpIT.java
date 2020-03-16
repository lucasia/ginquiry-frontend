package com.lucasia.ginquiryfrontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasia.ginquiryfrontend.controller.BrandClientController;
import com.lucasia.ginquiryfrontend.model.Brand;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Log4j2
public class BrandHttpIT {

    @LocalServerPort
    private int port;

    @Value("${ginquiry.brands.endpoint}")
    private String brandsEndpoint;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String brandsLocalUrl;

    @BeforeEach
    void setUp() {
        brandsLocalUrl = "http://localhost:" + port + BrandClientController.BRAND_PAGE_PATH; // path to the UI

        testRestTemplate = testRestTemplate.withBasicAuth("guest", "guest");
    }


    @Test
    public void testAddNewBrandViaHttpBypassingUIController() throws Exception {
        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpEntity<Brand> request = new HttpEntity<>(brand, new HttpHeaders());
        final ResponseEntity<String> results = this.testRestTemplate.postForEntity(new URI(brandsEndpoint),
                request, String.class);

        Assertions.assertTrue(results.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(results.getBody().contains(brand.getName()));
    }

    @Test
    public void testAddNewBrandViaHttpViaUIController() throws Exception {
        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpEntity<Brand> request = new HttpEntity<>(new HttpHeaders());

        final String brandParam = "?name=" + brand.getName();
        final ResponseEntity<String> postResults = this.testRestTemplate.postForEntity(new URI(brandsLocalUrl + "/post" + brandParam),
                request, String.class);


        // first redirects us back to the home page
        Assertions.assertTrue(postResults.getStatusCode().is3xxRedirection());

        // now check that our new brand is added
        final ResponseEntity<String> getResults = this.testRestTemplate.getForEntity(new URI(brandsLocalUrl), String.class);

        Assertions.assertTrue(getResults.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(getResults.getBody().contains(brand.getName()));
    }

    @Test
    public void testAddNewBrandViaHttpUsingJsonBypassingUIController() throws Exception {
        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String brandJson = new ObjectMapper().writeValueAsString(brand);

        final HttpEntity<String> request = new HttpEntity<>(brandJson, headers);

        final String results = this.testRestTemplate.postForObject(new URI(brandsEndpoint), request, String.class);

        Assertions.assertNotNull(results);
        Assertions.assertTrue(results.contains(brand.getName()));
    }

}