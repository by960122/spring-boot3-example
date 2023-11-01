package com.example.model;

import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2022/10/18
 * @description:
 */
@Data
public class OrderModel {
    private Long orderId;

    private String buyerName;

    private String sellerName;

    private double amount;
}
