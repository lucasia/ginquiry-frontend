package com.lucasia.ginquiryfrontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasia.ginquiryfrontend.client.GinquiryClient;
import com.lucasia.ginquiryfrontend.model.Nameable;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.Matchers;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public abstract class AbstractCrudControllerTest<T extends Nameable> {

    public static final String GUEST_USER = "guest";

    @Autowired
    private MockMvc mockMvc;

    private String path;
    private String postPath;
    private DomainFactory<T> domainFactory;

    public AbstractCrudControllerTest(DomainFactory<T> domainFactory, @NonNull String path) {
        this.domainFactory = domainFactory;
        this.path = path;
        this.postPath = path+"/post";
    }

    @Test
    @WithMockUser(GUEST_USER)
    public void testFindAll() throws Exception {
        List<T> nameableList = Arrays.asList(domainFactory.newInstanceRandomName(), domainFactory.newInstanceRandomName());

        getPageAndVerifyAll(path, nameableList);
    }


    @Test
    @WithMockUser(GUEST_USER)
    public void testNewSucceeds() throws Exception {
        T nameable = domainFactory.newInstanceRandomName();

        final ResultActions resultActions = saveEntity(nameable);

        // controller save will first redirect
        resultActions.andDo(
                print())
                .andExpect(status().is3xxRedirection());


        // verify now on the brand page
        getPageAndVerifyAll(path, Collections.singletonList(nameable));
    }

    @Test
    @Disabled // TODO: put this test back.  Currently using basic auth and always logged in.
    public void testReturn401WhenNotLoggedIn() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get(path));

        resultActions.andDo(
                print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(IsEmptyString.emptyString()));
    }


    protected void testAddNewSucceeds(T nameable) throws Exception {
        final ResultActions resultActions = saveEntity(nameable);

        resultActions.andDo(
                print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(nameable.getName())));

    }

    protected ResultActions saveEntity(T nameable) throws Exception {
        Mockito.when(getRepository().save(nameable)).thenReturn(nameable);

        final String jsonContent = new ObjectMapper().writeValueAsString(nameable);

        final RequestBuilder requestBuilder = post(postPath)
                .param("name", nameable.getName());

        ResultActions perform = mockMvc.perform(requestBuilder);

        return perform;
    }

    protected void getPageAndVerifyAll(String path, List<T> nameableList) throws Exception {
        Mockito.when(getRepository().findAll()).thenReturn(nameableList);

        final ResultActions resultActions = mockMvc.perform(
                get(path))
                .andDo(print())
                .andExpect(status().isOk());


        for (T nameable : nameableList) {
            resultActions.andExpect(content().string(Matchers.containsString(nameable.getName())));
        }
    }

    public abstract GinquiryClient<T, Long> getRepository();

    public static abstract class DomainFactory<T> {
        abstract T newInstanceRandomName();
    }
}
