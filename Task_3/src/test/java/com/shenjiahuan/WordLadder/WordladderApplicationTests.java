package com.shenjiahuan.WordLadder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class WordladderApplicationTests {

    @Autowired
    private WordLadderController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        this.mockMvc.perform(post("/login")
                            .param("username", "SE418")
                            .param("password", "SE418"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'success':true}"));
    }

    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        this.mockMvc.perform(post("/login")
                .param("username", "invalid")
                .param("password", "invalidpassword"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'success':false}"));
    }

    @Test
    public void accessWordLadderUnauthenticatedThenReturn401() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                .param("from", "hello")
                .param("to", "world"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void accessWordLadderAuthenticatedThenOk() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                                .param("from", "hello")
                                .param("to", "world"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':0}"));
    }

    @Test
    @WithMockUser
    public void shouldHaveNoLadderStatus() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                                .param("from", "successful")
                                .param("to", "fail"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':-1}"));
    }

    @Test
    @WithMockUser
    public void accessWithoutParameters() throws Exception {
        this.mockMvc.perform(get("/wordladders"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':-3}"));
    }
}
