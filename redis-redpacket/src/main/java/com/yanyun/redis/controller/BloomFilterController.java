package com.yanyun.redis.controller;

import com.yanyun.redis.service.BloomFilterService;
import com.yanyun.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName：BloomFilterController
 * @Description：布隆过滤器控制层
 * @Author：chenyb
 * @Date：2020/8/29 10:59 下午
 * @Versiion：1.0
 */
@RestController
@RequestMapping("bloom")
public class BloomFilterController {
    @Autowired
    private BloomFilterService bloomFilterService;
    @Autowired
    private RedisService redisService;
    @GetMapping("idExists")
    public boolean ifExists(int id){
        return bloomFilterService.userIdExists(id);
    }

    /**
     * redis布隆过滤器判断是否存在
     * @param name
     * @param id
     * @return
     */
    @GetMapping("redisIdExists")
    public boolean redisIdExists(String name,int id){
        return redisService.bloomFilterExists(name,id);
    }

    /**
     * redis布隆过滤器添加
     * @param name
     * @param id
     * @return
     */
    @GetMapping("redisAdd")
    public boolean redisAdd(String name,int id){
        return redisService.bloomFilterAdd(name,id);
    }
}
