package com.lucasia.ginquiryfrontend.client;


import com.lucasia.ginquiryfrontend.model.Brand;
import com.lucasia.ginquiryfrontend.security.CustomFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value= BrandClient.BRAND_NAME,
        url="${ginquiry.brands.endpoint:http://localhost:8081"+ BrandClient.BRAND_PATH+"}",
        configuration = CustomFeignConfig.class)
public interface BrandClient extends GinquiryClient<Brand, Long>  {

    String BRAND_NAME = "brands";

    String BRAND_PATH = "/"+BRAND_NAME;
}
