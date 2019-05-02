package com.shenjiahuan.wordladder.play;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordLadderControllerTest {
    @Autowired
    WordLadderController controller;

    @Test
    public void testWordLadderSuccessful() {
        WordLadderResult result1 = controller.wordLadder("code", "data");
        assertEquals(0, result1.status);
        assertEquals(5, result1.result.size());
        WordLadderResult result2 = controller.wordLadder("cat", "dog");
        assertEquals(0, result2.status);
        assertEquals(4, result2.result.size());
        WordLadderResult result6 = controller.wordLadder("sleep", "awake");
        assertEquals(0, result6.status);
        assertEquals(10, result6.result.size());
        WordLadderResult result7 = controller.wordLadder("angel", "devel");
        assertEquals(0, result7.status);
        assertEquals(10, result7.result.size());
        WordLadderResult result8 = controller.wordLadder("random", "ransom");
        assertEquals(0, result8.status);
        assertEquals(2, result8.result.size());
        WordLadderResult result9 = controller.wordLadder("work", "play");
        assertEquals(0, result9.status);
        assertEquals(7, result9.result.size());
    }

    @Test
    public void testWordLadderNoExist() {
        WordLadderResult result3 = controller.wordLadder("rudy", "ruru");
        assertEquals(-1, result3.status);
        assertNull(result3.result);
        WordLadderResult result4 = controller.wordLadder("guost", "boo");
        assertEquals(-1, result4.status);
        assertNull(result4.result);
        WordLadderResult result5 = controller.wordLadder("marty", "keith");
        assertEquals(-1, result5.status);
        assertNull(result5.result);
    }
}
