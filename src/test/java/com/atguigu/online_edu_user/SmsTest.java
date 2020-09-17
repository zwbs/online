package com.atguigu.online_edu_user;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;


public class SmsTest {

    public static void main(String[] args) {
        Map<Object,Object> map = new HashMap<>();
        map.put("code", "2255");
        String s = JSON.toJSONString(map);
        System.out.println(s);
    }
}
