package com.monkeyzi.rocketmq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkeyzi.rocketmq.entity.MbootUser;
import com.monkeyzi.rocketmq.service.MbootUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    private MbootUserService mbootUserService;

    @GetMapping(value = "/index")
    public List index(){
        return mbootUserService.list(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,"123456"));
    }



}
