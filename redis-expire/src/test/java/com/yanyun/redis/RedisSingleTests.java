package com.yanyun.redis;

import com.alibaba.fastjson.JSON;
import com.yanyun.redis.entity.User;
import com.yanyun.redis.service.RedisService;
import com.yanyun.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSingleTests {


    @Resource
    private RedisService redisService;

    @Test
    public void contextLoads() {
        //测试redis的string类型
        redisService.setString("weichat", "程序员私房菜");
        log.info("我的微信公众号为：{}", redisService.getString("wechat"));

        // 如果是个实体，我们可以使用json工具转成json字符串，
        User user = new User("CSDN", 18);
        redisService.setString("userInfo", JSON.toJSONString(user));
        log.info("用户信息：{}", redisService.getString("userInfo"));


        //测试redis的hash类型
        redisService.setHash("users", "name", JSON.toJSONString(user));
        log.info("用户姓名：{}", redisService.getHash("user", "name"));

        //测试redis的list类型
        redisService.setList("list", "football");
        redisService.setList("list", "basketball");
        List<String> valList = redisService.getList("list", 0, -1);
        for (String value : valList) {
            log.info("list中有：{}", value);
        }
        //测试设置key失效时间
//        redisService.setTimeOut("CSDN", 9);
        //让当前线程休眠10秒
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("CSDN：", redisService.getString("CSDN"));
    }

    @Test
    public void testRedis() {
        //测试redis的string类型
        User user1 = new User("张三", 18);
        String json1 = JSON.toJSONString(user1);
        User user2 = new User("李四", 20);
        String json2 = JSON.toJSONString(user2);
        redisService.setList("user", json1);
        redisService.setList("user", json2);
        List<String> users = redisService.getList("user", 0, -1);
        log.info("user:{}", users);
    }

    @Test
    public void testRedisCluster() {
        //测试redis的string类型
        String str = "YanYun";
        redisService.setString(str, "NNX");
        redisService.setTimeOut(str, 20, TimeUnit.SECONDS);

        String string1 = redisService.getString(str);
        log.info("first str:{} -- {}", string1, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String string2 = redisService.getString(str);
        log.info("expire str:{} -- {}", string2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Autowired
    UserService userService;

    @Test
    public void testClusterCache() {
        //测试redis的string类型
        User uer1 = userService.getUserById(1L);
        User uer2 = userService.getUserById2(2L);
        log.info("first updateUser:{} -- getUser{}", uer1, uer2);
    }
}
