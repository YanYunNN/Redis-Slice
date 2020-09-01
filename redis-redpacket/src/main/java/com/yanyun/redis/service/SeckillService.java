package com.yanyun.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName：SeckillService
 * @Description：秒杀
 * @Author：chenyb
 * @Date：2020/8/30 12:38 下午
 * @Versiion：1.0
 */
@Service
public class SeckillService {
    @Autowired
    private RedisService redisService;
    //秒杀前缀
    private static final String secStartPrefix = "skuId_start_";
    //接受抢购数
    private static final String secAccess = "skuId_access_";
    //计算从0开始
    private static final String secCount = "skuId_count_";
    //布隆过滤器名称
    private static final String filterName = "skuId_bloomfilter_";
    private static final String bookName = "skuId_booked_";

    public String seckill(int uid, int skuId) {
        //一、流量拦截层
        //1.判断秒杀是否开始,0_1554045087,开始标识_开始时间
        String isStart = (String) redisService.get(secStartPrefix + skuId);
        if (isStart == null || "".equals(isStart)) {
            return "秒杀还未开始！";
        }
        if (isStart.contains("_")) {
            //秒杀标识符
            Integer isStartInt = Integer.parseInt(isStart.split("_")[0]);
            //秒杀开始的时间戳
            Integer startTime = Integer.parseInt(isStart.split("_")[1]);
            if (isStartInt == 0) {
                if (startTime > getNow()) { //秒杀时间戳大于当前时间
                    return "还未开始";
                } else {
                    //代表已经开始秒杀
                    redisService.set(secStartPrefix + skuId, 1+"");
                }
            } else {
                return "系统异常";
            }
        } else {
            if (Integer.parseInt(isStart) != 1) {
                return "系统异常";
            }
        }
        //流量拦截
        String skuIdAccessName = secAccess + skuId;
        //接受抢购数
        Integer accessNumInt = Integer.parseInt(redisService.get(skuIdAccessName).toString());
        String skuIdCountName = secCount + skuId;
        //秒杀总数
        Integer countNumInt = Integer.parseInt(redisService.get(skuIdCountName).toString());
        //抢购成功
        if (countNumInt > accessNumInt) {
            return "抢购已经完成，欢迎下次参与";
        } else {
            //接受抢购数原子加1
            redisService.incr(skuIdCountName, 1);
        }
        //二、信息校验层
        //判断布隆过滤器是否存在该uid
        if (redisService.bloomFilterExists(filterName, uid)) {
            return "你已经抢购过该商品，请勿重复下单！";
        } else {
            //将抢购的到用户加入到布隆过滤器
            redisService.bloomFilterAdd(filterName, uid);
        }
        //lua脚本原子递增
        Boolean isSuccess = redisService.getAndIncrLua(bookName + skuId);
        if (isSuccess) {
            //此处异步入库，加入到MQ消息队列，另外一个项目不停监控MQ队列，然后异步导入，代码自己完善
            return "恭喜你抢购成功";
        } else {
            return "抢购结束，欢迎下次参与！";
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    private long getNow() {
        return System.currentTimeMillis() / 1000;
    }
}
