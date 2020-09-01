package com.yanyun.redis.mapper;

import com.yanyun.redis.domain.RedPacketInfo;

import java.util.List;

public interface RedPacketInfoMapper {
    List<RedPacketInfo> ListRedPacketInfo();
    void insert(RedPacketInfo redPacketInfo);
}
