package com.yanyun.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @Auther: xcai
 * @Date: 2020/07/08/12:33
 * @Description:
 * @Version: 1.0
 */
@Configuration
public class RedisKeyConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }

    @Bean
    public KeyExpiredListener keyExpiredListener() {
        return new KeyExpiredListener(redisMessageListenerContainer());
    }

    /**
     * 订阅的key失效模式，比如0号库
     * @return
     */
    @Bean
    public PatternTopic patternTopic() {
        //0：只监听0库，*表示所有
        return new PatternTopic("__keyevent@*__:expired");
    }
}
