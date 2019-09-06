package com.monkeyzi.rocketmq.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.monkeyzi.rocketmq.common.core.exception.BusinessException;
import com.monkeyzi.rocketmq.common.db.service.SuperServiceImpl;
import com.monkeyzi.rocketmq.entity.Mtest;
import com.monkeyzi.rocketmq.mapper.MtestMapper;
import com.monkeyzi.rocketmq.service.MtestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MtestServiceImpl extends SuperServiceImpl<MtestMapper,Mtest>  implements MtestService {
    @Autowired
    private MtestMapper mtestMapper;
    private final String URL="http://localhost:8787/index";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String tra(){
        Mtest mtest=new Mtest();
        mtest.setName("111");
        //限制性本地事务
        int count=mtestMapper.insert(mtest);
        if (count>0){
            log.info("新增成功");
        }
        final HttpResponse execute = HttpRequest.get(URL).timeout(1000).execute();
        int status = execute.getStatus();
        if (status!=200){
            log.info("调用第三方接口发生异常！");
            throw new BusinessException("异常了");
        }
        return "ok";
    }

    @Override
    public String tra2() {
        Mtest mtest=new Mtest();
        mtest.setName("111");
        //限制性本地事务
        int count=mtestMapper.insert(mtest);
        if (count>0){
            log.info("新增成功");
        }
        return "ok";
    }
}
