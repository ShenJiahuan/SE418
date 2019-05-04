package com.shenjiahuan.wordladder.play;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordLadderTest {
    @Test
    public void testReadFile() throws java.io.IOException {
        WordLadder wordLadder = new WordLadder("/static/EnglishWords.txt");
    }

    @Test
    public void testMatch() {
        assertTrue(WordLadder.isNear("a", "b"));
        assertFalse(WordLadder.isNear("a", "a"));
        assertTrue(WordLadder.isNear("abc", "bbc"));
        assertTrue(WordLadder.isNear("abc", "abb"));
        assertFalse(WordLadder.isNear("abc", "abc"));
    }

    @Test
    public void testCombine() {
        ArrayList<String> a = new ArrayList<>(Arrays.asList("a", "b", "c"));
        ArrayList<String> b = new ArrayList<>(Arrays.asList("c", "d", "e"));
        ArrayList<String> c = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        ArrayList<String> ret = WordLadder.combine(a, b);
        assertEquals(c, ret);
    }
}
