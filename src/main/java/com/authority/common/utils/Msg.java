package com.authority.common.utils;

import java.util.HashMap;

/**
 * @ClassName Msg
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 19:22
 * @Version 1.0
 **/
public class Msg {

    private Msg() {}

    public static HashMap<String, Object> setResult(String customStatus, Object data, String message) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("custom_status", customStatus);
        map.put("data", data);
        map.put("message", message);
        return map;
    }

    public static HashMap<String, Object> setResult(String customStatus, Object data, String message, String token) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("custom_status", customStatus);
        map.put("data", data);
        map.put("message", message);
        map.put("token", token);
        return map;
    }
}
