package com.yanyun.redis.service.impl;

import com.yanyun.redis.dao.ArticleMapper;
import com.yanyun.redis.model.Article;
import com.yanyun.redis.service.ArticleService;
import com.yanyun.redis.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements ArticleService {

}
