package com.yanyun.redis;

import com.yanyun.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLikeTests {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        log.info("CSDN：", redisUtils.getString("CSDN"));
    }
}
