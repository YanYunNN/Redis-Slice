package com.yanyun.redis.service.impl;

import com.yanyun.redis.dao.UserMapper;
import com.yanyun.redis.model.User;
import com.yanyun.redis.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

}
