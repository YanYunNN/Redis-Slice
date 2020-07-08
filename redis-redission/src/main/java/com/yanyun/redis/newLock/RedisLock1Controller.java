package com.yanyun.redis.newLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 简单StringRedisTemplate分布式锁
 * 改进
 * @author yanyun
 * @date 2020/7/7 17:30
 */
@RestController
@RequestMapping("/redisLock")
@Slf4j
public class RedisLock1Controller {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 简单分布式锁
     * @缺陷 当一个请求花费时间大于锁过期时间，也就是锁过期了，然后第二个请求来了，这个时候第一个请求要解锁，解的🔒其实是第二个请求的锁，后续其他请求又可以加锁
     * @场景 高并发下不适用，过期时间可能会过短被删除，但也不宜过长
     * @改进 使用看门子线程，监视，不够就延时
     */
    @GetMapping("/testLock")
    public String deductStock() {
        String lockKey = "lockKey";
        String clientId = UUID.randomUUID().toString();
        try {
            //非原子性,受宕机会死锁->解决了原子问题
            //过期时间可能会过短被删除，但也不宜过长
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, "clientId", 10, TimeUnit.SECONDS);
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
            if (clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }
        return "end";
    }
}
