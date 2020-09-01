package com.yanyun.redis.service;

import com.yanyun.redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    RedisService redisService;
    public static User user = new User(1L, "LiFei", 18);

    @Cacheable(value = "c1", key = "#id")
    public User getUserById(Long id) {
        log.info("----------c1----------");
        return user;
    }

    @Cacheable(value = "c2", key = "#id")
    public User getUserById2(Long id) {
        log.info("----------c2----------");
        return user;
    }

    @Cacheable(value = "c3", keyGenerator = "myKeyGenerator")
    public User getUserById3(Long id) {
        log.info("----------c2----------");
        return user;
    }

    @CachePut(value = "c1", key = "#user.id")
    public User update(User user) {
        return user;
    }

    @CacheEvict(value = "c1", allEntries = false)
    public void del(int id) {
    }

}
