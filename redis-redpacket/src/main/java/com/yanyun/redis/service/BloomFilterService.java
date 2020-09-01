package com.yanyun.redis.service;

import com.yanyun.redis.domain.SysUser;
import com.yanyun.redis.mapper.SysUserMapper;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @ClassName：BloomFilterService
 * @Description：布隆过滤器
 * @Author：chenyb
 * @Date：2020/8/29 10:38 下午
 * @Versiion：1.0
 */
@Service
public class BloomFilterService {
    @Autowired
    private SysUserMapper sysUserMapper;
    //布隆过滤器
    private BloomFilter<Integer> bf;
    /**
     * PostConstruct：程序启动时加载此方法
     */
    @PostConstruct
    public void initBloomFilter(){
        List<SysUser> sysUsers = sysUserMapper.allUserInfo();
        if (CollectionUtils.isEmpty(sysUsers)){
            return;
        }
        //初始化布隆过滤器
        bf=BloomFilter.create(Funnels.integerFunnel(),sysUsers.size());
        for (SysUser sysUser:sysUsers){
            bf.put(sysUser.getId());
        }
    }

    /**
     * 判断id可能存在布隆过滤器里面
     * @param id
     * @return
     */
    public boolean userIdExists(int id){
        return bf.mightContain(id);
    }
}
