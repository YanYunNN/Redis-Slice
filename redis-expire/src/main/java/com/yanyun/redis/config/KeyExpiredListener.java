package com.yanyun.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

/**
 * @Auther: xcai
 * @Date: 2020/07/08/12:28
 * @Description: Redis key过期监听器
 * @Version: 1.0
 * @缺点： 1.不管消息有没收到，只会发一次
 * 2. 存在订单不能被取消但消息被消费的可能性；
 * 3. 如果存在集群，那么每一个客户端都会收到key失效event，比如重复取消订单
 * 4. 可能会引入Redis分布式锁-再更新
 */
@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        String patternStr = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("key 失效: channel:{},key:{},pattern:{}", channel, key, patternStr);
    }
}
