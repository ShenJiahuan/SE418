package com.shenjiahuan.WordLadder;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

public class TestWordLadder {
    @Test
    public void testReadFile() throws java.io.IOException {
        WordLadder wordLadder = new WordLadder("src/main/resources/SmallDict.txt");
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

    @Test
    public void testWordLadder() throws java.io.IOException {
        File dir = new File("src/test/resources/traces/");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            String tracefile = file.getAbsolutePath();
            FileInputStream fin = new FileInputStream(tracefile);
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader buffReader = new BufferedReader(reader);
            String filename = "src/main/resources/" + buffReader.readLine();
            String src = buffReader.readLine();
            String dest = buffReader.readLine();
            buffReader.readLine();
            buffReader.readLine();
            String tmp = buffReader.readLine();
            int value = 0;
            if (!tmp.equals("no ladder exists")) {
                value = Integer.parseInt(tmp);
            }

            WordLadder wordLadder = new WordLadder(filename);
            ArrayList<String> ladder = wordLadder.get(src, dest);
            if (ladder == null) {
                assertEquals("Failed in " + tracefile, value, 0);
            } else {
                assertEquals("Failed in " + tracefile, value + 1, ladder.size());
            }
        }
    }
}
