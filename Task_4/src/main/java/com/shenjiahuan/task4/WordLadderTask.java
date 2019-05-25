package com.shenjiahuan.task4;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class WordLadderTask implements Callable<Object> {

    private WordLadder wordLadder;
    private String from;
    private String to;

    WordLadderTask(WordLadder wordLadder, String from, String to) {
        this.wordLadder = wordLadder;
        this.from = from;
        this.to = to;
    }

    @Override
    public Object call() throws Exception
    {
        ArrayList<String> result = null;
        int status = 0;

        if (from == null || to == null) {
            status = -3;
            return new WordLadderResult(result, status);
        }

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
