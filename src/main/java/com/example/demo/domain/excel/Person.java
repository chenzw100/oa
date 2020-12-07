package com.example.demo.domain.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class Person {
    /**
     * name是excel里的表头，一定是一一对应
     */
    @Excel(name = "姓名", orderNum = "0")
    private String name;

    @Excel(name = "性别", replace = {"男_1", "女_2"}, orderNum = "1")
    private String sex;

    /**
     * 导出excel时可以使用exportFormat这个属性设置日期格式，
     * 导入的时候可以使用importFormat这个属性，
     * 使用format相当于设置了前面两个，例如你可以这样设置：@Excel(name = "生产时间",exportFormat = "yyyy-mm-dd hh:mm:ss")
     */
    @Excel(name = "生日", format = "yyyy-MM-dd", orderNum = "2")
    private Date birthday;

    public Person(){}
    public Person(String name, String sex, Date birthday) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
