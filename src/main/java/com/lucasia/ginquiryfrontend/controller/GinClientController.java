package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.BrandClient;
import com.lucasia.ginquiryfrontend.GinClient;
import model.Booze;
import model.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GinClientController extends AbstractClientController<Booze, Long>{

    public static final String GIN_PAGE_NAME = "gin";

    public GinClientController(GinClient client) {
        super(client);
    }

    @Override
    @GetMapping("/"+GIN_PAGE_NAME)
    public String findAll(Model model) {
        return super.findAll(model);
    }

    @PostMapping("/"+GIN_PAGE_NAME+"/post")
    public String save(@RequestParam String objName,
                       @RequestParam String description,
                       @RequestParam String brandName,
                       Model model) {
        if (objName != null && !objName.trim().isEmpty()) {

            final Booze dto = newInstance(objName);
            dto.setDescription(description);
            dto.setBrand(new Brand(brandName));

            getClient().save(dto);

        }
        return "redirect:/"+ getPageName();
    }

    @Override
    public Booze newInstance(String objName) {
        return new Booze(objName);
    }

    @Override
    public String getPageName() {
        return GIN_PAGE_NAME;
    }
}
