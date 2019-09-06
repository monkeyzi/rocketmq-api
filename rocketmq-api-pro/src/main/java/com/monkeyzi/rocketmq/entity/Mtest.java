package com.monkeyzi.rocketmq.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "m_test")
public class Mtest {

    @TableId
    private Integer id;

    private String  name;

}
