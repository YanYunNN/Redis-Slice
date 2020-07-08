package com.yanyun.redis.newLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 简单StringRedisTemplate分布式锁
 * 存在超时问题（高并发情景）
 * @author yanyun
 * @date 2020/7/7 17:30
 */
@RestController
@RequestMapping("/redisLock")
@Slf4j
public class RedisLockController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 简单分布式锁
     * @缺陷 当一个请求花费时间大于锁过期时间，也就是锁过期了，然后第二个请求来了，这个时候第一个请求要解锁，解的🔒其实是第二个请求的锁，后续其他请求又可以加锁
     * @场景 高并发下不适用
     */
    @GetMapping("/testLock")
    public String deductStock() {
        String lockKey = "lockKey";
        try {
            //非原子性,受宕机会死锁
            /*Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, "YanYun");
            redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);*/
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, "YanYun", 10, TimeUnit.SECONDS);
            if (!result) {
                return "error_code";
            }
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock--;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else System.out.println("库存不足！");
        } finally {
            //解锁
            redisTemplate.delete(lockKey);
        }
        return "end";
    }
}
