package com.lucasia.ginquiryfrontend;


import model.Booze;
import model.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        value="gins",
        url="${ginquiry.gins.endpoint:http://localhost:8081/gins}")
public interface GinClient extends GinquiryClient<Booze, Long> {
}
