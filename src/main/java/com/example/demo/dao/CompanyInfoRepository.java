package com.example.demo.dao;

import com.example.demo.domain.table.CompanyInfo;
import com.example.demo.domain.table.StockZy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by czw on 2018/10/19.
 * JpaRepository default method
 * User user=new User();
 userRepository.findAll();
 userRepository.findOne(1l);
 userRepository.save(user);
 userRepository.delete(user);
 userRepository.count();
 userRepository.exists(1l);
 */
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo,Long> {
    CompanyInfo save(CompanyInfo companyInfo);
    List<CompanyInfo> findByName(String name);


}
