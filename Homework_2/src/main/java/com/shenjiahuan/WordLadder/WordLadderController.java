package com.shenjiahuan.WordLadder;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class WordLadderController {
    private WordLadder wordLadder;

    @PostConstruct
    private void init() {
        try {
            this.wordLadder = new WordLadder("/static/EnglishWords.txt");
        } catch (IOException | NullPointerException ex) {
            this.wordLadder = null;
        }
    }

    @RequestMapping({"/wordladders"})
    public WordLadderResult wordLadder(@RequestParam("from") String from, @RequestParam("to") String to) {
        ArrayList<String> result = null;
        int status = 0;

        try {
            result = this.wordLadder.get(from, to);
            if (result == null) {
                status = -1;
            }
        } catch (NullPointerException ex) {
            status = -2;
        }

        return new WordLadderResult(result, status);
    }
}
