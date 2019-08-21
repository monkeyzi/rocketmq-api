package com.monkeyzi.rocketmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {
    //订单状态
    private Long orderId;
    //订单描述
    private String orderDesc;

}
