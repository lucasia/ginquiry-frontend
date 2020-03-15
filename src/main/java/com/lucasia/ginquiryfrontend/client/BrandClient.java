package com.lucasia.ginquiryfrontend.client;


import com.lucasia.ginquiryfrontend.SecurityConfiguration;
import com.lucasia.ginquiryfrontend.ServerSecurityConfiguration;
import com.lucasia.ginquiryfrontend.model.Brand;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value="brands",
        url="${ginquiry.brands.endpoint:http://localhost:8081/brands}",
        configuration = SecurityConfiguration.class)
public interface BrandClient extends GinquiryClient<Brand, Long>  {

}
