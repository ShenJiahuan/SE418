package com.shenjiahuan.WordLadder;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
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
    public void accessSecuredResourceUnauthenticatedThenReturn401() throws Exception {
        this.mockMvc.perform(get("/wordladders")
                .param("from", "hello")
                .param("to", "world"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
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

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
