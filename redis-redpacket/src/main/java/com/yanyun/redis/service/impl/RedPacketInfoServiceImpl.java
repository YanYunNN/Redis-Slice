package com.yanyun.redis.service.impl;

import com.yanyun.redis.domain.RedPacketInfo;
import com.yanyun.redis.mapper.RedPacketInfoMapper;
import com.yanyun.redis.service.RedPacketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName：RedPacketInfoServiceImpl
 * @Description：红包信息
 * @Author：chenyb
 * @Date：2020/8/24 10:36 下午
 * @Versiion：1.0
 */
@Service
public class RedPacketInfoServiceImpl implements RedPacketInfoService {
    @Autowired
    private RedPacketInfoMapper redPacketInfoMapper;

    /**
     * 添加红包
     * @param redPacketInfo
     */
    @Override
    public void insert(RedPacketInfo redPacketInfo) {
        redPacketInfoMapper.insert(redPacketInfo);
    }
}
