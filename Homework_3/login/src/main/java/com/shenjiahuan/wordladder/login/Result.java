package com.shenjiahuan.wordladder.login;

import java.io.Serializable;
import java.util.ArrayList;

public class Result implements Serializable {
    public ArrayList<String> result;
    public int status;

    public Result() {}

    public Result(ArrayList<String> result, int status) {
        this.result = result;
        this.status = status;
    }
}
