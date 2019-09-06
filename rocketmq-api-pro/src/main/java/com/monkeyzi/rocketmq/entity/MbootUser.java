package com.monkeyzi.rocketmq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MbootUser {

    private Long id;

    private String username;

    private String password;

    private Integer userType;

    private Integer deptId;

    private LocalDateTime createTime=LocalDateTime.now();


}
