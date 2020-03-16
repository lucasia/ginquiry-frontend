package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.client.GinquiryClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
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

    public T findById(ID id) {
        throw new UnsupportedOperationException("FindById not implemented");
    }

    public T findByName(String name) {
        throw new UnsupportedOperationException("FindByName not implemented");
    }


    public String findAll(Model model) {
        if (model.containsAttribute("name")) {
            String name = (String) model.asMap().get("name");
            model.addAttribute("greeting", String.format("%s %s", greeting, name));
        }

        final List<T> models = findAll();

        model.addAttribute("models", models);

        return getPageName();
    }

    public List<T> findAll() {
        return client.findAll();
    }

    public String save(@RequestParam(value="name") String objName, Model model) {
        if (objName != null && !objName.trim().isEmpty()) {

            final T dto = newInstance(objName);

            final T saved = save(dto);
        }

        return "redirect:/" + getPageName();
    }

    private T save(T dto) {
        return client.save(dto);
    }

    public abstract T newInstance(String objName);

    public abstract String getPageName();

    public GinquiryClient<T, ID> getClient() {
        return client;
    }


}
