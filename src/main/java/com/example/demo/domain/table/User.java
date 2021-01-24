package com.example.demo.domain.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * INSERT INTO `my`.`my_user`(`id`, `enable`, `info_level`, `name`, `password`, `phone`) VALUES (1, '启用', '管理员', 'admin', 'DC483E80A7A0BD9EF71D8CF973673924', '1234567899');
 */
@Entity(name="my_user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,columnDefinition="varchar(50) COMMENT '姓名'")
    private String name;
    @Column(nullable = false,columnDefinition="varchar(100) COMMENT '电话'")
    private String phone;
    @Column(nullable = true,columnDefinition="varchar(10) COMMENT '级别'")
    private String infoLevel;
    @Column(nullable = false,columnDefinition="varchar(100) COMMENT '密码'")
    private String password;
    @Column(nullable = true,columnDefinition="varchar(10) COMMENT '可用'")
    private String enable;
    @Column(nullable = true,columnDefinition="varchar(10) COMMENT '部门'")
    private String depName;

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", infoLevel='" + infoLevel + '\'' +
                ", password='" + password + '\'' +
                ", enable='" + enable + '\'' +
                '}';
    }
}
