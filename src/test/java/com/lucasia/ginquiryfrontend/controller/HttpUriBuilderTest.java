package com.lucasia.ginquiryfrontend.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

public class HttpUriBuilderTest {

    @Test
    public void testURI() throws URISyntaxException {
        String hostname = "lucasia.rules.com";
        int port = 1737;

        final String expectedBaseUri = "http://" + hostname + ":" + port;

        final HttpUriBuilder httpUriBuilder = new HttpUriBuilder(hostname, port);

        Assertions.assertEquals(expectedBaseUri, httpUriBuilder.baseUri().toString());
        Assertions.assertEquals(expectedBaseUri+"/brand", httpUriBuilder.clientBrandUri().toString());
        Assertions.assertEquals(expectedBaseUri+"/gin", httpUriBuilder.clientGinUri().toString());
        Assertions.assertEquals(expectedBaseUri+"/login", httpUriBuilder.clientLoginUri().toString());
    }
}
