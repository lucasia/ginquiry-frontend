package com.lucasia.ginquiryfrontend.client;


import com.lucasia.ginquiryfrontend.SecurityConfiguration;
import com.lucasia.ginquiryfrontend.model.Booze;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value= GinClient.GINS_NAME,
        url="${ginquiry.gins.endpoint:http://localhost:8081"+GinClient.GINS_PATH+"}",
        configuration = SecurityConfiguration.class)
public interface GinClient extends GinquiryClient<Booze, Long> {

    public final String GINS_NAME = "gins";

    public final String GINS_PATH = "/"+GINS_NAME;
}
