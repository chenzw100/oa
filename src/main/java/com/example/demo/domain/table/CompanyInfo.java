package com.example.demo.domain.table;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 */
@Entity(name="company_info")
public class CompanyInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Excel(name = "公司", orderNum = "0")
    @Column(nullable = true,columnDefinition="varchar(500) COMMENT '公司'")
    private String name;
    @Excel(name = "专业", orderNum = "2")
    @Column(nullable = true,columnDefinition="varchar(2000) COMMENT '专业'")
    private String zy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }
}
