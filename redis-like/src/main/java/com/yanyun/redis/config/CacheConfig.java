package com.yanyun.redis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Bean
    RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .prefixKeysWith("YanYun::")
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(1));
        configMap.put("c1", redisCacheConfig);

        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheManager redisCacheManager = new RedisCacheManager(cacheWriter,
                RedisCacheConfiguration.defaultCacheConfig(),
                configMap);
        return redisCacheManager;
    }

}
