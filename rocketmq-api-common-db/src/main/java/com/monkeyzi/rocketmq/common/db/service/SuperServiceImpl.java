package com.monkeyzi.rocketmq.common.db.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 高yg
 * @date: 2019/6/20 21:17
 * @qq:854152531@qq.com
 * @description:
 */
@Slf4j
public class SuperServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements ISuperService<T> {


}
