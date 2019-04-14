package com.shenjiahuan.WordLadder;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void testActuatorStatus() throws Exception {
        this.mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'status':'UP'}"));
    }

    @Test
    public void testActuatorInfo() throws Exception {
        this.mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }
}
