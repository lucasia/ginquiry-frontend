package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.controller.BrandClientController;
import com.lucasia.ginquiryfrontend.controller.GinClientController;
import com.lucasia.ginquiryfrontend.controller.LoginController;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpUriBuilder {
    public static final String LOCALHOST = "localhost";

    private String hostname;
    private int port;

    public HttpUriBuilder(int port) {
        this(LOCALHOST, port);
    }

    public HttpUriBuilder(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    // public URI(String scheme, String userInfo, String host, int port, String path, String query, String fragment)
    public URI baseUri() throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(hostname);
        builder.setPort(port);

        return builder.build();
    }

    public URI clientBrandUri() throws URISyntaxException {
        return new URIBuilder(baseUri()).setPath(BrandClientController.BRAND_PAGE_PATH).build();
    }

    public URI clientGinUri() throws URISyntaxException {
        return new URIBuilder(baseUri()).setPath(GinClientController.GIN_PAGE_NAME).build();
    }

    public URI clientLoginUri() throws URISyntaxException {
        return new URIBuilder(baseUri()).setPath(LoginController.LOGIN_PATH).build();
    }
}
