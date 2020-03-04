package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.GinquiryClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
public abstract class AbstractClientController<T, ID extends Long> {

    @Value("${greeting:Hello}")
    private String greeting;

    private GinquiryClient<T, ID> client;

    public AbstractClientController(GinquiryClient<T, ID> client) {
        this.client = client;
    }

    public String findAll(Model model) {
        if (model.containsAttribute("name")) {
            String name = (String) model.asMap().get("name");
            model.addAttribute("greeting", String.format("%s %s", greeting, name));
        }

        final List<T> models = client.findAll();

        model.addAttribute("models", models);

        return getPageName();
    }

    public String save(@RequestParam String objName, Model model) {
        if (objName != null && !objName.trim().isEmpty()) {

            final T dto = newInstance(objName);

            client.save(dto);

        }
        return "redirect:/"+ getPageName();
    }

    public abstract T newInstance(String objName);

    public abstract String getPageName();

    public GinquiryClient<T, ID> getClient() {
        return client;
    }
}
