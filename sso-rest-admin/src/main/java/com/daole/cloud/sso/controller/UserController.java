package com.daole.cloud.sso.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/sso/usr")
public class UserController {

    /**
     * 用户登录操作
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> dataMap = new HashMap<>();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "admin");
        userMap.put("roles", "admin");
        dataMap.put("data",userMap);
        return dataMap;
    }

    /**
     * 获取用户信息接口
     * @return
     */
    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "admin");
        userMap.put("roles", "admin");
        dataMap.put("data",userMap);
        return dataMap;
    }
}
