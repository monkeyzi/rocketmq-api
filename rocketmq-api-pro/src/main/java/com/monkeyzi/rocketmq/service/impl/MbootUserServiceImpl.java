package com.monkeyzi.rocketmq.service.impl;

import com.monkeyzi.rocketmq.common.db.service.SuperServiceImpl;
import com.monkeyzi.rocketmq.entity.MbootUser;
import com.monkeyzi.rocketmq.mapper.MbootUserMapper;
import com.monkeyzi.rocketmq.service.MbootUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MbootUserServiceImpl extends SuperServiceImpl<MbootUserMapper,MbootUser> implements MbootUserService {
}
