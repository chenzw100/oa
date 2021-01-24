package com.example.demo.domain.table;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 */
@Entity(name="company_zy")
public class CompanyZy implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Excel(name = "公司", orderNum = "0")
    @Column(nullable = true,columnDefinition="varchar(500) COMMENT '公司'")
    private String name;
    @Excel(name = "专业", orderNum = "1")
    @Column(nullable = true,columnDefinition="varchar(2000) COMMENT '专业'")
    private String zy;

    @Excel(name = "电话1", orderNum = "2")
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '电话1'")
    private String phoneOne;

    @Excel(name = "电话2", orderNum = "3")
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '电话2'")
    private String phoneTwo;

    @Excel(name = "电话3", orderNum = "4")
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '电话3'")
    private String phoneThree;

    @Excel(name = "电话4", orderNum = "5")
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '电话4'")
    private String phoneFour;
    @Excel(name = "电话5", orderNum = "6")
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '电话5'")
    private String phoneFive;

    @Column(nullable = true,columnDefinition="varchar(1000) COMMENT '描述'")
    private String infoDesc;
    @Excel(name = "意向客户", orderNum = "9")
    @Column(nullable = true,columnDefinition="varchar(20) COMMENT '意向客户'")
    private String customerYx;
    @Excel(name = "微信客户", orderNum = "10")
    @Column(nullable = true,columnDefinition="varchar(20) COMMENT '微信客户'")
    private String customerWx;
    @Excel(name = "作废号码", orderNum = "7")
    @Column(nullable = true,columnDefinition="varchar(20) COMMENT '作废'")
    private String customerZf;
    @Excel(name = "已拨打", orderNum = "8")
    @Column(nullable = true,columnDefinition="varchar(20) COMMENT '已拨打'")
    private String called;
    @Column(nullable = true,columnDefinition="bigint(20) COMMENT '操作人员'")
    private Long optId;
    @Column(nullable = true,columnDefinition="varchar(40) COMMENT '操作人员'")
    private String optName;
    @Transient
    private String fen;
    @Column(nullable = true,columnDefinition="datetime DEFAULT NULL COMMENT '更新时间'")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified;
    @Column(nullable = true,columnDefinition="datetime DEFAULT NULL COMMENT '分配时间'")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fenDate;

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

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public String getPhoneThree() {
        return phoneThree;
    }

    public void setPhoneThree(String phoneThree) {
        this.phoneThree = phoneThree;
    }

    public String getPhoneFour() {
        return phoneFour;
    }

    public void setPhoneFour(String phoneFour) {
        this.phoneFour = phoneFour;
    }

    public String getPhoneFive() {
        return phoneFive;
    }

    public void setPhoneFive(String phoneFive) {
        this.phoneFive = phoneFive;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
    }

    public String getCustomerYx() {
        return customerYx;
    }

    public void setCustomerYx(String customerYx) {
        this.customerYx = customerYx;
    }

    public String getCustomerWx() {
        return customerWx;
    }

    public void setCustomerWx(String customerWx) {
        this.customerWx = customerWx;
    }

    public String getCustomerZf() {
        return customerZf;
    }

    public void setCustomerZf(String customerZf) {
        this.customerZf = customerZf;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public Long getOptId() {
        return optId;
    }

    public void setOptId(Long optId) {
        this.optId = optId;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getFenDate() {
        return fenDate;
    }

    public void setFenDate(Date fenDate) {
        this.fenDate = fenDate;
    }
}
