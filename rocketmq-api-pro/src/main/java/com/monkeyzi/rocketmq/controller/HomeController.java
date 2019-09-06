package com.monkeyzi.rocketmq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkeyzi.rocketmq.entity.MbootUser;
import com.monkeyzi.rocketmq.service.MbootUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    @GetMapping(value = "/insert")
    public void insert(){
        MbootUser user=new MbootUser();
        user.setId(99L);
        user.setUsername("123456");
        user.setPassword("1111");
        user.setDeptId(1);
        user.setUserType(0);

        try {
            mbootUserService.save(user);
        }catch (DuplicateKeyException e){
            System.out.println(222);
        }
    }




}
