package com.shenjiahuan.WordLadder;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ActuatorTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void accessActuatorUnauthenticatedThenReturn401() throws Exception {
        this.mockMvc.perform(get("/actuator/health"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void testActuatorStatus() throws Exception {
        this.mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':'UP'}"));
    }

    @Test
    @WithMockUser
    public void testActuatorInfo() throws Exception {
        this.mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }
}
