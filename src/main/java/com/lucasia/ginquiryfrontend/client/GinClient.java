package com.lucasia.ginquiryfrontend.client;


import com.lucasia.ginquiryfrontend.SecurityConfiguration;
import com.lucasia.ginquiryfrontend.ServerSecurityConfiguration;
import com.lucasia.ginquiryfrontend.model.Booze;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value="gins",
        url="${ginquiry.gins.endpoint:http://localhost:8081/gins}",
        configuration = SecurityConfiguration.class)
public interface GinClient extends GinquiryClient<Booze, Long> {
}
