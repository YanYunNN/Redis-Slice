package com.yanyun.redis.controller;

import com.yanyun.redis.service.SeckillService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName：SeckillController
 * @Description：秒杀
 * @Author：chenyb
 * @Date：2020/8/30 12:34 下午
 * @Versiion：1.0
 */
@RestController
@RequestMapping("redis")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀-抢库存
     * @param uid   用户标识符id
     * @param skuId 库存id
     * @return
     */
    @GetMapping("seckill")
    public String seckill(@Param("uid") int uid, @Param("skuId") int skuId) {
        return seckillService.seckill(uid, skuId);
    }
}
