package com.yanyun.redis.service.impl;

import com.yanyun.redis.domain.RedPacketRecord;
import com.yanyun.redis.mapper.RedPacketRecordMapper;
import com.yanyun.redis.service.RedPacketRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName：RedPacketRecordServiceImpl
 * @Description：TODO
 * @Author：chenyb
 * @Date：2020/8/25 9:56 下午
 * @Versiion：1.0
 */
@Service
public class RedPacketRecordServiceImpl implements RedPacketRecordService {
    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;

    @Override
    public void insert(RedPacketRecord redPacketRecord) {
        redPacketRecordMapper.insert(redPacketRecord);
    }
}
