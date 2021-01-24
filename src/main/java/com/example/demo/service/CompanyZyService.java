package com.example.demo.service;

import com.example.demo.dao.CompanyZyRepository;
import com.example.demo.domain.table.CompanyZy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CompanyZyService {

    @Autowired
    CompanyZyRepository companyZyRepository;
    public void delete(CompanyZy companyZy){
        companyZyRepository.delete(companyZy);
    }
    public Page<CompanyZy> findALl(Integer pageNumber,Integer pageSize,CompanyZy companyZy){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(companyZy.getOptName())){
            companyZy.setOptName(null);
        }
        if("".equals(companyZy.getCustomerWx())){
            companyZy.setCustomerWx(null);
        }
        if("".equals(companyZy.getCustomerYx())){
            companyZy.setCustomerYx(null);
        }
        if("".equals(companyZy.getCustomerZf())){
            companyZy.setCustomerZf(null);
        }
        if("".equals(companyZy.getCalled())){
            companyZy.setCalled(null);
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
        Example<CompanyZy> example = Example.of(companyZy/*,matcher*/);
        Page<CompanyZy> all = companyZyRepository.findAll(example,pageable);
        return all;
    }
    public CompanyZy saveOrUpdate(CompanyZy companyZy){
        return companyZyRepository.save(companyZy);
    }
    public CompanyZy getById(Long id){
        return companyZyRepository.getOne(id);
    }

    public List<CompanyZy> findAll(){
       return companyZyRepository.findAll();
    }
    public CompanyZy findByPhone(String phone){
        List<CompanyZy> list = companyZyRepository.findCompanyZyByPhoneOne(phone);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Page<CompanyZy> fenpeiList(Integer pageNumber,Integer pageSize,CompanyZy companyZy){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(companyZy.getCustomerWx())){
            companyZy.setCustomerWx(null);
        }
        if("".equals(companyZy.getCustomerYx())){
            companyZy.setCustomerYx(null);
        }
        if("".equals(companyZy.getCustomerZf())){
            companyZy.setCustomerZf(null);
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
        if("是".equals(companyZy.getFen())){
            if(StringUtils.isEmpty(companyZy.getZy())){
                return  companyZyRepository.findByAndOptIdIsNull(pageable);
            }
            return companyZyRepository.findByAndOptIdIsNullAndZyContaining(companyZy.getZy(),pageable);
        }else if("否".equals(companyZy.getFen())){
            if(StringUtils.isEmpty(companyZy.getZy())){
                return companyZyRepository.findByAndOptIdIsNotNull(pageable);
            }
            return companyZyRepository.findByAndOptIdIsNotNullAndZyContaining(companyZy.getZy(),pageable);
        }
        return null;
    }
    public List<CompanyZy> findFirst10(){
        return companyZyRepository.findFirst10ByCustomerWx("是");
    }
    public List<CompanyZy> findExport(CompanyZy CompanyZy){
        if("".equals(CompanyZy.getCustomerWx())){
            CompanyZy.setCustomerWx(null);
        }
        if("".equals(CompanyZy.getCustomerYx())){
            CompanyZy.setCustomerYx(null);
        }
        if("".equals(CompanyZy.getCustomerZf())){
            CompanyZy.setCustomerZf(null);
        }
        Example<CompanyZy> example = Example.of(CompanyZy);
        List<CompanyZy> all = companyZyRepository.findAll(example);
        return all;
    }

    @Transactional
    public Integer repeatDelete(){
        return companyZyRepository.repeatDelete();
    }

    @Transactional
    public Integer fenPei(Long userId,String optName,Long[] ids){
        return companyZyRepository.fenPei(userId,optName,ids);
    }


}
