package com.yanyun.redis.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanyun.redis.model.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 基本PO字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "base")
public class BasePO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //使用@TableLogic注解实现逻辑删除
    @TableLogic
    @TableField(value = "deleted", exist = true)
    protected Integer deleted = 0;

    @TableField(value = "gmt_create", exist = true)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime gmtCreate;

    @TableField(value = "gmt_modified", exist = true)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime gmtModified;

    @Override
    public String toString() {
        return "BasePO{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
