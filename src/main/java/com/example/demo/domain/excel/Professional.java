package com.example.demo.domain.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 */
public class Professional implements Serializable {

    @Excel(name = "姓名", orderNum = "0")
    private String name;
    @Excel(name = "电话", orderNum = "1")
    private String phone;
    @Excel(name = "专业", orderNum = "2")
    private String zy;
    public Professional(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }
}
