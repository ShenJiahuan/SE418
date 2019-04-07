package com.shenjiahuan.WordLadder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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
    public void shouldHaveSuccessStatus() throws Exception {
        this.mockMvc.perform(get("/wordladder?from=hello&to=world")).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"status\":0")));
    }

    @Test
    public void shouldHaveNoLadderStatus() throws Exception {
        this.mockMvc.perform(get("/wordladder?from=success&to=fail")).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"status\":-1")));
    }

}
