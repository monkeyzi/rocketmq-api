package com.monkeyzi.rocketmq.controller;

import com.alibaba.excel.EasyExcel;
import com.monkeyzi.rocketmq.entity.DownloadData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/excel")
public class EasyExcelController {

    @GetMapping(value = "/down")
    public void downExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
    }


    @PostMapping(value = "/up")
    public String  upExcel(MultipartFile file) throws IOException {
        if (file==null){
            System.out.println("文件不能为空");
            return "error";
        }
        EasyExcel.read(file.getInputStream(),DownloadData.class,new DemoDataListener()).sheet().doRead();
        System.out.println("上传成功");
        return "success";

    }


    public List<DownloadData> data(){
        List<DownloadData> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            DownloadData data=new DownloadData();
            data.setDate(new Date());
            data.setDoubleData((double) i);
            data.setString("name"+i);
            list.add(data);
        }
        return list;
    }
}
