package com.lucasia.ginquiryfrontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasia.ginquiryfrontend.controller.BrandClientController;
import com.lucasia.ginquiryfrontend.controller.LoginController;
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
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    private String clientBaseUrl;

    private String clientBrandUrl;

    private String clientLoginUrl;

    @BeforeEach
    void setUp() {
        clientBaseUrl = "http://localhost:" + port;

        clientLoginUrl = clientBaseUrl + LoginController.LOGIN_PATH;

        clientBrandUrl = clientBaseUrl + BrandClientController.BRAND_PAGE_PATH; // path to the UI

        // testRestTemplate = testRestTemplate.withBasicAuth("guest", "guest");
    }


    @Test
    public void testAddNewBrandViaHttpBypassingUIController() throws Exception {
        testRestTemplate = testRestTemplate.withBasicAuth("guest", "guest");

        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpEntity<Brand> request = new HttpEntity<>(brand, new HttpHeaders());
        final ResponseEntity<String> results = this.testRestTemplate.postForEntity(new URI(brandsEndpoint),
                request, String.class);

        Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
        Assertions.assertTrue(results.getBody().contains(brand.getName()));
    }

    @Test
    public void testAddNewBrandViaHttpUsingJsonBypassingUIController() throws Exception {
        testRestTemplate = testRestTemplate.withBasicAuth("guest", "guest");

        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String brandJson = new ObjectMapper().writeValueAsString(brand);

        final HttpEntity<String> request = new HttpEntity<>(brandJson, headers);

        final String results = this.testRestTemplate.postForObject(new URI(brandsEndpoint), request, String.class);

        Assertions.assertNotNull(results);
        Assertions.assertTrue(results.contains(brand.getName()));
    }

    @Test
    public void testGetBrandViaUIController() throws Exception {
        testRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES); // needed to pass the cookies

        final ResponseEntity<String> loginResponse = loginAndAssertSuccess();

        // logged in, should be able to navigate to the page now
        final ResponseEntity<String> getBrandResults = this.testRestTemplate.getForEntity(new URI(clientBrandUrl), String.class);

        Assertions.assertEquals(HttpStatus.OK, getBrandResults.getStatusCode());
        Assertions.assertTrue(getBrandResults.getBody().contains("Rock Rose")); // assume there's some brands setup already
    }

    @Test
    public void testAddNewBrandViaHttpViaUIController() throws Exception {
        testRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES); // needed to pass the cookies

        final Brand brand = new Brand(UUID.randomUUID().toString());

        final ResponseEntity<String> loginResponse = loginAndAssertSuccess();

        final HttpEntity<MultiValueMap<String, String>> brandRequest = createBrandRequest(brand);

        final ResponseEntity<String> postBrandResponse = this.testRestTemplate.postForEntity(new URI(clientBrandUrl + "/post"), brandRequest, String.class);
        Assertions.assertEquals(HttpStatus.FOUND, postBrandResponse.getStatusCode());

        // now check that our new brand is added
        final ResponseEntity<String> getBrandResults = this.testRestTemplate.getForEntity(new URI(clientBrandUrl), String.class);
        Assertions.assertEquals(HttpStatus.OK, getBrandResults.getStatusCode());
        Assertions.assertTrue(getBrandResults.getBody().contains(brand.getName())); // assume there's some brands setup already
    }

    private HttpEntity<MultiValueMap<String, String>> createLoginRequest() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", LoginController.GUEST_USER);
        map.add("password", "guest");

        return new HttpEntity<>(map, headers);
    }

    private ResponseEntity<String> loginAndAssertSuccess() throws URISyntaxException {
        final HttpEntity<MultiValueMap<String, String>> loginRequest = createLoginRequest();

        final ResponseEntity<String> loginResponse = this.testRestTemplate.exchange(new URI(clientLoginUrl), HttpMethod.POST, loginRequest, String.class);

        final HttpHeaders loginResponseHeaders = loginResponse.getHeaders();

        Assertions.assertEquals(clientBaseUrl+"/", loginResponseHeaders.getLocation().toString());  // redirects us to home
        List<String> setCookieHeader = loginResponseHeaders.get("Set-Cookie");  // confirm that login set the cookie
        Assertions.assertFalse(setCookieHeader.isEmpty());
        Assertions.assertTrue(setCookieHeader.get(0).contains("JSESSIONID"));
        Assertions.assertEquals(HttpStatus.FOUND, loginResponse.getStatusCode());

        return loginResponse;
    }

    protected HttpEntity<MultiValueMap<String, String>> createBrandRequest(Brand brand) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", brand.getName());

        return new HttpEntity<>(map, headers);
    }



}