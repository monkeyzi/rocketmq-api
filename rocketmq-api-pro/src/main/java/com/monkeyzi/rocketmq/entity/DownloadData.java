package com.monkeyzi.rocketmq.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

@Data
@HeadRowHeight(20)
@ContentRowHeight(30)
@ColumnWidth(25)
public class DownloadData {
    @ExcelProperty("字符串标题")
    private String string;

    @ColumnWidth(40)
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
}
