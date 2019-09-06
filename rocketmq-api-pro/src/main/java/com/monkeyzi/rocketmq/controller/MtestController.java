package com.monkeyzi.rocketmq.controller;

import com.monkeyzi.rocketmq.service.MtestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MtestController {

    @Autowired
    private MtestService mtestService;

    @GetMapping(value = "/tra")
    public String  test(){
       String ok=mtestService.tra();
       return ok;
    }
    @GetMapping(value = "/tra2")
    public String  test2(){
        String ok=mtestService.tra2();
        return ok;
    }
}
