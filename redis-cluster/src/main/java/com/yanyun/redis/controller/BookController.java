package com.yanyun.redis.controller;

import com.alibaba.fastjson.JSON;
import com.yanyun.redis.entity.RestResponse;
import com.yanyun.redis.entity.User;
import com.yanyun.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    RedisService redisService;

    @GetMapping("/test1")
    public RestResponse test1() {
        User user = new User("张三", 199);
        String jsonString = JSON.toJSONString(user);
        redisService.setList("user", jsonString);
        List<String> users = redisService.getList("user", 0, -1);
        return RestResponse.good(users);
    }
}
