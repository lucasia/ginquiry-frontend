package com.lucasia.ginquiryfrontend.controller;

import com.lucasia.ginquiryfrontend.client.BrandClient;
import com.lucasia.ginquiryfrontend.client.GinquiryClient;
import com.lucasia.ginquiryfrontend.model.Brand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static com.lucasia.ginquiryfrontend.controller.BrandClientController.BRAND_PAGE_PATH;

@WebMvcTest(BrandClientController.class) // run without the server
@Log4j2
public class BrandClientControllerTest extends AbstractCrudControllerTest<Brand> {

    @MockBean
    private BrandClient brandClient;

    public BrandClientControllerTest() {
        super(new BrandDomainFactory(), BRAND_PAGE_PATH);
    }

    @Override
    public GinquiryClient<Brand, Long> getRepository() {
        return brandClient;
    }

    public static class BrandDomainFactory extends DomainFactory<Brand> {

        @Override
        public Brand newInstanceRandomName() {
            return new Brand(UUID.randomUUID().toString());
        }
    }

}
