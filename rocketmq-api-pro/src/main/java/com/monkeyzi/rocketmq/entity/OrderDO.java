package com.monkeyzi.rocketmq.entity;

import lombok.Data;

@Data
public class OrderDO {
    //订单状态
    private Long orderId;
    //订单描述
    private String orderDesc;

}
