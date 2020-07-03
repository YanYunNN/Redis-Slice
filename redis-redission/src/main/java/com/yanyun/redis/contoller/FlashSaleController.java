package com.yanyun.redis.contoller;

import com.yanyun.redis.redis.DistributedRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 分布式Redis锁秒杀测试
 * @author xcai
 * @date 2020/6/11 17:30
 */
@RestController
@RequestMapping("/lock")
@Slf4j
public class FlashSaleController {

    @Autowired
    private DistributedRedisLock distributedRedisLock;

    //总库存
    private long totalNum = 0;
    //商品key名字
    private String shangpingKey = "computer_key";
    //获取锁的超时时间 秒
    private int timeout = 1 * 1000;

    @GetMapping("/flashSale")
    public List<String> flashSale() {
        //抢到商品的用户
        List<String> shopUsers = new ArrayList<>();
        //构造很多用户
        List<String> users = new ArrayList<>();
        IntStream.range(0, 10000).parallel().forEach(user -> users.add("用户-" + user));

        //初始化库存
        totalNum = 10;

        //模拟开抢
        users.parallelStream().forEach(user -> {
            String shopUser = flash(user);
            if (!StringUtils.isEmpty(shopUser)) {
                shopUsers.add(shopUser);
            }
        });
        return shopUsers;
    }

    /**
     * 模拟抢单动作
     * @param user
     * @return
     */
    private String flash(String user) {
        //用户开抢时间
        long startTime = System.currentTimeMillis();
        //未抢到的情况下，30秒内继续获取锁
        while ((startTime + timeout) >= System.currentTimeMillis()) {
            //商品是否剩余
            if (totalNum <= 0) {
                break;
            }
            if (distributedRedisLock.spLock(shangpingKey)) {
                //用户b拿到锁
                log.info("用户{}拿到锁...", user);
                try {
                    //商品是否剩余
                    if (totalNum <= 0) {
                        break;
                    }
                    //模拟生成订单耗时操作，方便查看：神牛-50 多次获取锁记录
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //抢购成功，商品递减，记录用户
                    totalNum -= 1;
                    //抢单成功跳出
                    log.info("用户{}抢单成功跳出...所剩库存：{}", user, totalNum);
                    return user + "抢单成功，所剩库存：" + totalNum;
                } finally {
                    log.info("用户{}释放锁...", user);
                    //释放锁
                    distributedRedisLock.unlock(shangpingKey);
                }
            } else {
                //用户b没拿到锁，在超时范围内继续请求锁，不需要处理
//                if (user.equals("神牛-50") || user.equals("神牛-69")) {
//                    logger.info("用户{}等待获取锁...", user);
//                }
            }
        }
        return "";
    }

}
