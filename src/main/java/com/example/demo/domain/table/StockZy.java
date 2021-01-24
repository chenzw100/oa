package com.example.demo.domain.table;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 */
@Entity(name="stock_zy")
public class StockZy implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Excel(name = "姓名", orderNum = "0")
    @Column(nullable = true,columnDefinition="varchar(50) COMMENT '姓名'")
    private String name;
    @Excel(name = "专业", orderNum = "2")
    @Column(nullable = true,columnDefinition="varchar(100) COMMENT '专业'")
    private String zy;
    @Column(nullable = true,columnDefinition="varchar(100) COMMENT '地区'")
    @Excel(name = "地区", orderNum = "4")
    private String infoCity;
    @Excel(name = "电话", orderNum = "1")
    @Column(nullable = true,columnDefinition="varchar(100) COMMENT '电话'")
    private String phone;
    @Column(nullable = true,columnDefinition="varchar(10) COMMENT '级别'")
    private String infoLevel;
    @Column(nullable = true,columnDefinition="varchar(1000) COMMENT '描述'")
    private String infoDesc;
    @Excel(name = "意向客户", orderNum = "5")
    @Column(nullable = true,columnDefinition="varchar(20) COMMENT '意向客户'")
    private String customerYx;
    @Excel(name = "微信客户", orderNum = "6")
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
    @Column(nullable = true,columnDefinition="datetime COMMENT '更新时间'")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified;
    @Column(nullable = true,columnDefinition="datetime COMMENT '分配时间'")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fenDate;

    public Date getFenDate() {
        return fenDate;
    }

    public void setFenDate(Date fenDate) {
        this.fenDate = fenDate;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
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

    public String getCustomerZf() {
        return customerZf;
    }

    public void setCustomerZf(String customerZf) {
        this.customerZf = customerZf;
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

    public StockZy() {
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfoLevel() {
        return infoLevel;
    }

    public void setInfoLevel(String infoLevel) {
        this.infoLevel = infoLevel;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
    }

    public String getInfoCity() {
        return infoCity;
    }

    public void setInfoCity(String infoCity) {
        this.infoCity = infoCity;
    }

    @Override
    public String toString() {
        return "StockZy{" +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", zy='" + zy + '\'' +
                ", infoCity='" + infoCity + '\'' +
                ", infoLevel='" + infoLevel + '\'' +
                ", infoDesc='" + infoDesc + '\'' +
                '}';
    }
    public String toExportUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("?temp=1");
        if(StringUtils.isNotEmpty(customerWx)){
            sb.append("&customerWx=").append(customerWx);
        }
        if(StringUtils.isNotEmpty(customerYx)){
            sb.append("&customerYx=").append(customerYx);
        }
        if(StringUtils.isNotEmpty(customerZf)){
            sb.append("&customerZf=").append(customerZf);
        }

        return sb.toString();
    }
}
