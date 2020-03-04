package com.lucasia.ginquiryfrontend;


import model.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        value="brands",
        url="${ginquiry.brands.endpoint:http://localhost:8081/brands}")
public interface BrandClient {

    @RequestMapping(method= RequestMethod.GET, path="/")
    List<Brand> findAll();

    @RequestMapping(method=RequestMethod.GET, path="/{id}")
    Brand findById(@PathVariable("id") long messageId);

    @RequestMapping(method=RequestMethod.POST, path="/")
    Brand save(@RequestBody Brand message);
}
