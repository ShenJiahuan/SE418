package com.shenjiahuan.wordladder.play;

import java.util.ArrayList;

public class WordLadderResult {
    public ArrayList<String> result;
    public int status;

    public WordLadderResult(ArrayList<String> result, int status) {
        this.result = result;
        this.status = status;
    }
}
