package com.yanyun.redis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanyun.redis.model.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
