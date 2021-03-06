package com.example.demo.service;

import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.StockZy;
import com.example.demo.domain.table.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Component
public class StockZyService {

    @Autowired
    StockZyRepository stockZyRepository;
    public void delete(StockZy stockZy){
        stockZyRepository.delete(stockZy);
    }
    public Page<StockZy> findALl(Integer pageNumber,Integer pageSize,StockZy stockZy){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(stockZy.getOptName())){
            stockZy.setOptName(null);
        }
        if("".equals(stockZy.getCustomerWx())){
            stockZy.setCustomerWx(null);
        }
        if("".equals(stockZy.getCustomerYx())){
            stockZy.setCustomerYx(null);
        }
        if("".equals(stockZy.getCustomerZf())){
            stockZy.setCustomerZf(null);
        }
        if("".equals(stockZy.getCalled())){
            stockZy.setCalled(null);
        }
        if("".equals(stockZy.getName())){
            stockZy.setName(null);
        }
        if("".equals(stockZy.getPhone())){
            stockZy.setPhone(null);
        }
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"fenDate");
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"id");
        //如果有多个排序条件 建议使用此种方式 使用 Sort.by 替换之前的  new Sort();
        Sort sort = Sort.by(order,order1);
        //使用 PageRequest.of 替代之前的 new PageRequest();
        /**
         * page：0 开始
         * size:每页显示的数量
         * 排序的规则
         */
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
       /* ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("customerWx", match -> match.startsWith())
                .withMatcher("customerYx", match -> match.startsWith());*/
        Example<StockZy> example = Example.of(stockZy/*,matcher*/);
        Page<StockZy> all =null;
        if(stockZy.getModifiedStart()!=null){
            if(stockZy.getModifiedEnd()==null){
                stockZy.setModifiedEnd(new Date());
            }else {
                long time =stockZy.getModifiedEnd().getTime()+24*60*60*1000-1;
                stockZy.setModifiedEnd(new Date(time));
            }
            if(stockZy.getCalled()==null){
                all = stockZyRepository.findByModifiedBetween(stockZy.getModifiedStart(),stockZy.getModifiedEnd(),pageable);
            }else {
                all = stockZyRepository.findByModifiedBetweenAndCalled(stockZy.getModifiedStart(),stockZy.getModifiedEnd(),stockZy.getCalled(),pageable);
            }
        }else {
            all = stockZyRepository.findAll(example,pageable);
        }
        return all;
    }
    public Page<StockZy> findSignDateALl(Integer pageNumber,Integer pageSize,StockZy stockZy){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(stockZy.getOptName())){
            stockZy.setOptName(null);
        }
        if("".equals(stockZy.getCustomerZf())){
            stockZy.setCustomerZf(null);
        }
        if("".equals(stockZy.getName())){
            stockZy.setName(null);
        }
        if("".equals(stockZy.getPhone())){
            stockZy.setPhone(null);
        }
        stockZy.setCalled("是");
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"fenDate");
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"id");
        //如果有多个排序条件 建议使用此种方式 使用 Sort.by 替换之前的  new Sort();
        Sort sort = Sort.by(order,order1);
        //使用 PageRequest.of 替代之前的 new PageRequest();
        /**
         * page：0 开始
         * size:每页显示的数量
         * 排序的规则
         */
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
       /* ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("customerWx", match -> match.startsWith())
                .withMatcher("customerYx", match -> match.startsWith());*/
        Example<StockZy> example = Example.of(stockZy/*,matcher*/);
        Page<StockZy> all =null;
        if(stockZy.getSignDateStart()!=null){
            if(stockZy.getSignDateEnd()==null){
                stockZy.setSignDateEnd(new Date());
            }else {
                long time =stockZy.getModifiedEnd().getTime()+24*60*60*1000-1;
                stockZy.setSignDateEnd(new Date(time));
            }
            all = stockZyRepository.findBySignDateBetweenAndCalled(stockZy.getSignDateStart(),stockZy.getSignDateEnd(),stockZy.getCalled(),pageable);
        }else {
            all = stockZyRepository.findAll(example,pageable);
        }
        return all;
    }
    public StockZy saveOrUpdate(StockZy stockZy){
        return stockZyRepository.save(stockZy);
    }
    public StockZy getById(Long id){
        return stockZyRepository.getOne(id);
    }

    public List<StockZy> findAll(){
       return stockZyRepository.findAll();
    }
    public StockZy findByPhone(String phone){
        List<StockZy> list = stockZyRepository.findStockZyByPhone(phone);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Page<StockZy> fenpeiList(Integer pageNumber,Integer pageSize,StockZy stockZy){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(stockZy.getCustomerWx())){
            stockZy.setCustomerWx(null);
        }
        if("".equals(stockZy.getCustomerYx())){
            stockZy.setCustomerYx(null);
        }
        if("".equals(stockZy.getCustomerZf())){
            stockZy.setCustomerZf(null);
        }
        if("".equals(stockZy.getOptName())){
            stockZy.setOptName(null);
        }

        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"fenDate");
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"id");
        //如果有多个排序条件 建议使用此种方式 使用 Sort.by 替换之前的  new Sort();
        Sort sort = Sort.by(order,order1);
        //使用 PageRequest.of 替代之前的 new PageRequest();
        /**
         * page：0 开始
         * size:每页显示的数量
         * 排序的规则
         */
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        if("是".equals(stockZy.getFen())){
            if(StringUtils.isEmpty(stockZy.getZy())){
                return  stockZyRepository.findByAndOptIdIsNull(pageable);
            }
            return stockZyRepository.findByAndOptIdIsNullAndZyContaining(stockZy.getZy(),pageable);
        }else if("否".equals(stockZy.getFen())){
            if(StringUtils.isEmpty(stockZy.getZy())){
                if(StringUtils.isEmpty(stockZy.getOptName())){
                    return stockZyRepository.findByAndOptIdIsNotNull(pageable);
                }
                return stockZyRepository.findByOptName(stockZy.getOptName(),pageable);
            }
            return stockZyRepository.findByAndOptIdIsNotNullAndZyContaining(stockZy.getZy(),pageable);
        }
        return null;
    }
    public List<StockZy> findFirst10(){
        return stockZyRepository.findFirst10ByCustomerWx("是");
    }
    public List<StockZy> findExport(StockZy stockZy){
        if("".equals(stockZy.getCustomerWx())){
            stockZy.setCustomerWx(null);
        }
        if("".equals(stockZy.getCustomerYx())){
            stockZy.setCustomerYx(null);
        }
        if("".equals(stockZy.getCustomerZf())){
            stockZy.setCustomerZf(null);
        }
        if("".equals(stockZy.getOptName())){
            stockZy.setOptName(null);
        }
        Example<StockZy> example = Example.of(stockZy);
        List<StockZy> all = stockZyRepository.findAll(example);
        return all;
    }

    @Transactional
    public Integer repeatDelete(){
        return stockZyRepository.repeatDelete();
    }

    @Transactional
    public Integer fenPei(Long userId,String optName,Long[] ids){
        return stockZyRepository.fenPei(userId,optName,ids);
    }
    @Transactional
    public Integer resetFP(Date date){
        return stockZyRepository.resetFP(date);
    }


}
