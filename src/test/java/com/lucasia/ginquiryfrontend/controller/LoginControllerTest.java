package com.lucasia.ginquiryfrontend.controller;

import lombok.extern.log4j.Log4j2;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(LoginController.class) // run without the server
@Log4j2
public class LoginControllerTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void testRedirectsToLoginPageWhenNotLoggedIn() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/anotherurl"));

        String loginUrl = "http://localhost/login";

        resultActions.andDo(
                print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(loginUrl));
    }

    @Test
    public void testGoesToLogin() throws Exception {
        final ResultActions loginResultsActions = mockMvc.perform(get(LoginController.LOGIN_PATH));

        MvcResult mvcResult = loginResultsActions.andDo(
                print())
                .andExpect(content().string(Matchers.containsString("User Name: ")))
                .andExpect(content().string(Matchers.containsString("Password: ")))
                .andReturn();

        log.debug(mvcResult);

    }

    @Test
    public void testLoginSucceeds() throws Exception {
        MvcResult mvcResult = loginGuest(mockMvc);

        log.debug(mvcResult);
    }

    public static MvcResult loginGuest(MockMvc mockMvc) throws Exception {
        final RequestBuilder requestBuilder = post(LoginController.LOGIN_PATH)
                .param("username", LoginController.GUEST_USER)
                .param("password", "guest");

        final ResultActions loginResultsActions = mockMvc.perform(requestBuilder);

        MvcResult mvcResult = loginResultsActions.andDo(
                print())
                .andExpect(status().is3xxRedirection())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andReturn();

        return mvcResult;
    }


}
