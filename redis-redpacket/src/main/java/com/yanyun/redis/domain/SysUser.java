package com.yanyun.redis.domain;

/**
 * @ClassName：SysUser
 * @Description：TODO
 * @Author：chenyb
 * @Date：2020/8/29 10:43 下午
 * @Versiion：1.0
 */
public class SysUser {
    /**
     * 自增内码
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 头像
     */
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
