package com.yanyun.redis.contoller;

import com.example.springboot_redis.redis.DistributedRedisLock;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式Redis锁测试controller
 * @author qp
 * @date 2019/7/19 17:30
 */
@RestController
@RequestMapping("/lock")
@Slf4j
public class LockTestController {

    @Autowired
    private DistributedRedisLock distributedRedisLock;

    // 测试分布式锁
    @GetMapping("/testLock")
    public void testLock() {
        for (int i = 0; i < 5; i++) {
            int num = i;
            new Thread("线程：" + i) {
                @Override
                public void run() {
                    Boolean lockFlag = distributedRedisLock.lock("LOCK");
                    log.info("{}加锁:{}", this.getName(), lockFlag);
                }
            }.start();
        }
    }
}
