package com.example.demo.service;

import com.example.demo.dao.CompanyInfoRepository;
import com.example.demo.domain.table.CompanyInfo;
import com.example.demo.domain.table.StockZy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyInfoService {

    @Autowired
    CompanyInfoRepository companyInfoRepository;

    public Page<CompanyInfo> findALl(Integer pageNumber,Integer pageSize,CompanyInfo companyInfo){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"id");
        //如果有多个排序条件 建议使用此种方式 使用 Sort.by 替换之前的  new Sort();
        Sort sort = Sort.by(order);
        //使用 PageRequest.of 替代之前的 new PageRequest();
        /**
         * page：0 开始
         * size:每页显示的数量
         * 排序的规则
         */
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Example<CompanyInfo> example = Example.of(companyInfo/*,matcher*/);
        Page<CompanyInfo> all = companyInfoRepository.findAll(example,pageable);
        return all;
    }

    public List<CompanyInfo> findExport(CompanyInfo stockZy){
        Example<CompanyInfo> example = Example.of(stockZy);
        List<CompanyInfo> all = companyInfoRepository.findAll(example);
        return all;
    }

}
