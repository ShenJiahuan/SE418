package com.shenjiahuan.wordladder.login;

import org.json.JSONObject;

class JWTResult {
    public static String fillResultString(Integer status, Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("status", status);
            put("result", result);
        }};

        return jsonObject.toString();
    }
}
