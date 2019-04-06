package com.shenjiahuan.WordLadder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordLadderController {
    public WordLadderController() {
    }

    @RequestMapping({"/wordladder"})
    public WordLadderResult wordLadder(@RequestParam("from") String from, @RequestParam("to") String to) {
        ArrayList<String> result = null;
        int status = 0;

        try {
            result = (new WordLadder("/static/EnglishWords.txt")).get(from, to);
            if (result == null) {
                status = -1;
            }
        } catch (IOException | NullPointerException ex) {
            status = -2;
        }

        return new WordLadderResult(result, status);
    }
}
