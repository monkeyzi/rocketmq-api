package com.monkeyzi.rocketmq.config;

import com.monkeyzi.rocketmq.common.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: 高yg
 * @date: 2019/6/20 20:51
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Configuration
@MapperScan({"com.monkeyzi.rocketmq.mapper*"})
@EnableTransactionManagement
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {
}
