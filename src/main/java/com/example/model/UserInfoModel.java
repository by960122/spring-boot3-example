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
    private int age;
    private float salary;
    private String address;
    private String remark;
    private Date createTime;
    private String birthDate;
}
