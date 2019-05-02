package com.shenjiahuan.wordladder.play;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PlayApplicationTests {

    @Autowired
    private WordLadderController controller;

    @Autowired
    private MockMvc mockMvc;

    public static String getToken() {
        return TokenAuthenticationService.createToken("SE418");
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void accessWordLadderUnauthenticatedThenReturn401() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                .param("from", "hello")
                .param("to", "world"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void accessWordLadderAuthenticatedThenOk() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                                .param("from", "hello")
                                .param("to", "world")
                                .header("Authorization", getToken()))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':0}"));
    }

    @Test
    public void shouldHaveNoLadderStatus() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                                .param("from", "successful")
                                .param("to", "fail")
                                .header("Authorization", getToken()))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':-1}"));
    }

    @Test
    public void accessWithoutParameters() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                                .header("Authorization", getToken()))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':-3}"));
    }
}
