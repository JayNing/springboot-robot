package com.example.robotdemo.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 测试日期格式数据转json
 *
 * @author ning
 * @create 2018-06-07 14:56
 **/
public class UserDTO {
    private String username;
    private Integer age;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }
}