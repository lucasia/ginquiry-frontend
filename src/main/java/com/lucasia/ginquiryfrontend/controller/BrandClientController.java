package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.BrandClient;
import model.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BrandClientController extends AbstractClientController<Brand, Long>{

    public BrandClientController(BrandClient client) {
        super(client);
    }

    @Override
    @GetMapping("/brand")
    public String findAll(Model model) {
        return super.findAll(model);
    }

    @Override
    @PostMapping("/brand/post")
    public String save(String objName, Model model) {
        return super.save(objName, model);
    }

    @Override
    public Brand newInstance(String objName) {
        return new Brand(objName);
    }

    @Override
    public String getPageName() {
        return "brand";
    }
}
