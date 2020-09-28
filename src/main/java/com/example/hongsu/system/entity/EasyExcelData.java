package com.example.hongsu.system.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 *  @author:WJ
 *  @date: 2020-05-23 14:40
 *  @describe:
 **/

public class EasyExcelData {

    //@ExcelProperty(value="字符串标题",index=0)
    private String string;
    //@ExcelProperty(value="日期标题",index=1)
    private Date date;
    //@ExcelProperty(value="数字标题",index=2)
    private Double doubleData;

    public EasyExcelData(){};

    public EasyExcelData(String string,Date date , Double doubleData){
        this.string = string;
        this.date = date;
        this.doubleData = doubleData;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }
}
