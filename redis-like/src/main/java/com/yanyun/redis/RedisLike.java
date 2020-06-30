package com.yanyun.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisLike {
    public static void main(String[] args) {
        SpringApplication.run(RedisLike.class, args);
    }
}
