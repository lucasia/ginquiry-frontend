package com.lucasia.ginquiryfrontend;


import model.Brand;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value="brands",
        url="${ginquiry.brands.endpoint:http://localhost:8081/brands}",
        configuration = FeignClientConfiguration.class)
public interface BrandClient extends GinquiryClient<Brand, Long>  {

}
