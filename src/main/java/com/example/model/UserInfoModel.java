package com.example.model;

import java.util.Date;

import lombok.Data;

/***
 * @author: BYDylan
 * @date: 2022/2/21
 * @description:
 */
@Data
public class UserInfoModel {
    private String name;
    private Integer age;
    private Float salary;
    private String address;
    private String remark;
    private Date createTime;
    private String birthDate;
}
