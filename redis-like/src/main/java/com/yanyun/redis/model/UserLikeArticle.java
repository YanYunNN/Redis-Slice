package com.yanyun.redis.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点赞中间表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeArticle extends BasePO {
    /**
     * 用户ID
     */
    @TableField(value = "user_id", exist = true)
    private Long userId;

    /**
     * 文章ID
     */
    @TableField(value = "article_id", exist = true)
    private Long articleId;
}
