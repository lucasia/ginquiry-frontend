package com.lucasia.ginquiryfrontend.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface GinquiryClient<T, ID extends Long> {

    @RequestMapping(method= RequestMethod.GET, path="/")
    List<T> findAll();

    @RequestMapping(method=RequestMethod.GET, path="/{id}")
    T findById(@PathVariable("id") ID messageId);

    @RequestMapping(method=RequestMethod.POST, path="/")
    T save(@RequestBody T message);

    @RequestMapping(method=RequestMethod.GET, path="/&name={name}")
    T findByName(@PathVariable("name") String name);
}
