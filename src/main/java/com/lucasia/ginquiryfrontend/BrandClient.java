package com.lucasia.ginquiryfrontend;


import domain.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        value="brands",
        url="${ginquiry.brands.endpoint:http://localhost:8081/brands}")
public interface BrandClient {//extends JpaRepository<Brand, Long> {

    @RequestMapping(method= RequestMethod.GET, path="/")
    List<Brand> findAll();

    @RequestMapping(method=RequestMethod.GET, path="/{id}")
    Brand findById(@PathVariable("id") long messageId);

    @RequestMapping(method=RequestMethod.POST, path="/")
    Brand save(@RequestBody Brand message);
}
