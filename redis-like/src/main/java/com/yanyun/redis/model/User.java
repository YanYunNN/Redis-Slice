package com.yanyun.redis.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasePO {
    /**
     * 用户名
     */
    @TableField(value = "username", exist = true)
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password", exist = true)
    private String password;
}
