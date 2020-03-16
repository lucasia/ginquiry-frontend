package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.client.BrandClient;
import com.lucasia.ginquiryfrontend.model.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BrandClientController extends AbstractClientController<Brand, Long>{

    public static final String BRAND_PAGE_NAME = "brand";

    public static final String BRAND_PAGE_PATH = "/"+BRAND_PAGE_NAME;

    public BrandClientController(BrandClient client) {
        super(client);
    }

    @Override
    @GetMapping(BRAND_PAGE_PATH)
    public String findAll(Model model) {
        return super.findAll(model);
    }

    @Override
    @PostMapping(BRAND_PAGE_PATH+"/post")
    public String save(@RequestParam(value="name") String objName, Model model) {
        return super.save(objName, model);
    }

    @Override
    @GetMapping(BRAND_PAGE_PATH+"/{id}")
    public Brand findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    @GetMapping(BRAND_PAGE_PATH+"/&name={name}")
    public Brand findByName(String name) {
        return super.findByName(name);
    }

    @Override
    public Brand newInstance(String objName) {
        return new Brand(objName);
    }

    @Override
    public String getPageName() {
        return BRAND_PAGE_NAME;
    }
}
