package com.yanyun.redis.newLock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xcai
 * @Date: 2020/07/07/21:07
 * @Description: 续命🔒
 * @Version: 1.0
 */
@RestController
public class RedissonController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;

    @GetMapping("/redissonLock")
    public String deductStock() {
        //Redisson取锁
        String lockKey = "lockKey";
        RLock redissonLock = redisson.getLock(lockKey);

        try {
            //加锁
            //原子性的实现，依赖于Lua脚本
            redissonLock.lock(); //setIfAbsent(lockKey, "clientId", 30, TimeUnit.SECONDS)，续命时候为设置时间的1/3

            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock--;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else System.out.println("库存不足！");
        } finally {
            //解锁
            redissonLock.unlock();
        }
        return "end";
    }
}
