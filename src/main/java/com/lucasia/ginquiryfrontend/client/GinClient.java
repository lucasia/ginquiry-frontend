package com.lucasia.ginquiryfrontend.client;


import com.lucasia.ginquiryfrontend.model.Booze;
import com.lucasia.ginquiryfrontend.security.CustomFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value= GinClient.GINS_NAME,
        url="${ginquiry.gins.endpoint:http://localhost:8081"+GinClient.GINS_PATH+"}",
        configuration = CustomFeignConfig.class)
public interface GinClient extends GinquiryClient<Booze, Long> {

    String GINS_NAME = "gins";

    String GINS_PATH = "/"+GINS_NAME;
}
