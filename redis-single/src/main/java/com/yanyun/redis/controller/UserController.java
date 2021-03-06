package com.yanyun.redis.controller;

import com.alibaba.fastjson.JSON;
import com.yanyun.redis.entity.RestResponse;
import com.yanyun.redis.entity.User;
import com.yanyun.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    RedisService redisService;

    /**
     * @return
     */
    @GetMapping("/test1")
    public RestResponse<?> getTimeTableByDate(@RequestParam long date) {
        User user = new User("张三", 18);
        String jsonString = JSON.toJSONString(user);
        redisService.setList("user", jsonString);
        return RestResponse.good("OK");
    }
}
