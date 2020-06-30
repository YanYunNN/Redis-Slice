package com.yanyun.redis.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

/**
 * 文章实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BasePO {
    /**
     * 用户ID
     */
    @TableField(value = "user_id", exist = true)
    private Long userId;

    /**
     * 文章内容
     */
    @TableField(value = "content", exist = true)
    private Blob content;

    /**
     * 文章总点赞数
     */
    @TableField(value = "total_like_count", exist = true)
    private Long totalLikeCount;

    /**
     * 文章名字
     */
    @TableField(value = "article_name", exist = true)
    private String articleName;

}
