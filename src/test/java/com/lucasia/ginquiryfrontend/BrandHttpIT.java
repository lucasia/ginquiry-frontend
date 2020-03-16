package com.lucasia.ginquiryfrontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasia.ginquiryfrontend.controller.HttpUriBuilder;
import com.lucasia.ginquiryfrontend.controller.LoginController;
import com.lucasia.ginquiryfrontend.model.Brand;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private HttpUriBuilder uriBuilder;

    @Value("${spring.security.user.name}")
    private String username;

    @Value(("${spring.security.user.password}"))
    private String password;

    @BeforeEach
    void setUp() {
        uriBuilder = new HttpUriBuilder(port);
    }

    @Test
    public void testAddNewBrandViaHttpBypassingUIController() throws Exception {
        final TestRestTemplate testRestTemplate = new TestRestTemplate().withBasicAuth(username, password);

        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpEntity<Brand> request = new HttpEntity<>(brand, new HttpHeaders());
        final ResponseEntity<String> results = testRestTemplate.postForEntity(new URI(brandsEndpoint),
                request, String.class);

        Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
        Assertions.assertTrue(results.getBody().contains(brand.getName()));
    }


    @Test
    public void testAddNewBrandViaHttpUsingJsonBypassingUIController() throws Exception {
        final TestRestTemplate testRestTemplate = new TestRestTemplate().withBasicAuth(username, password);


        final Brand brand = new Brand(UUID.randomUUID().toString());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String brandJson = new ObjectMapper().writeValueAsString(brand);

        final HttpEntity<String> request = new HttpEntity<>(brandJson, headers);

        final String results = testRestTemplate.postForObject(new URI(brandsEndpoint), request, String.class);

        Assertions.assertNotNull(results);
        Assertions.assertTrue(results.contains(brand.getName()));
    }

    @Test
    public void testGetBrandViaUIController() throws Exception {
        final TestRestTemplate testRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES); // needed to pass the cookies

        final ResponseEntity<String> loginResponse = loginAndAssertSuccess(testRestTemplate);

        // logged in, should be able to navigate to the page now
        final ResponseEntity<String> getBrandResults = testRestTemplate.getForEntity(uriBuilder.clientBrandUri(), String.class);

        Assertions.assertEquals(HttpStatus.OK, getBrandResults.getStatusCode());
        Assertions.assertTrue(getBrandResults.getBody().contains("Rock Rose")); // assume there's some brands setup already
    }

    @Test
    public void testAddNewBrandViaHttpViaUIController() throws Exception {
        final TestRestTemplate testRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES); // needed to pass the cookies

        final Brand brand = new Brand(UUID.randomUUID().toString());

        final ResponseEntity<String> loginResponse = loginAndAssertSuccess(testRestTemplate);

        final HttpEntity<MultiValueMap<String, String>> brandRequest = createBrandRequest(brand);

        final ResponseEntity<String> postBrandResponse = testRestTemplate.postForEntity(
                new URI(uriBuilder.clientBrandUri().toString() + "/post"), brandRequest, String.class);
        Assertions.assertEquals(HttpStatus.FOUND, postBrandResponse.getStatusCode());

        // now check that our new brand is added
        final ResponseEntity<String> getBrandResults = testRestTemplate.getForEntity(uriBuilder.clientBrandUri(), String.class);
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

    private ResponseEntity<String> loginAndAssertSuccess(TestRestTemplate testRestTemplate) throws URISyntaxException {
        final HttpEntity<MultiValueMap<String, String>> loginRequest = createLoginRequest();

        final ResponseEntity<String> loginResponse = testRestTemplate.exchange(uriBuilder.clientLoginUri(), HttpMethod.POST, loginRequest, String.class);

        final HttpHeaders loginResponseHeaders = loginResponse.getHeaders();

        Assertions.assertEquals(uriBuilder.baseUri()+"/", loginResponseHeaders.getLocation().toString());  // redirects us to home
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