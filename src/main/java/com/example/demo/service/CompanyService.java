package com.example.demo.service;

import com.example.demo.dao.CompanyRepository;
import com.example.demo.domain.table.Company;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    public void delete(Company Company){
        companyRepository.delete(Company);
    }
    public Page<Company> findALl(Integer pageNumber,Integer pageSize,Company Company){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(Company.getOptName())){
            Company.setOptName(null);
        }
        if("".equals(Company.getCustomerWx())){
            Company.setCustomerWx(null);
        }
        if("".equals(Company.getCustomerYx())){
            Company.setCustomerYx(null);
        }
        if("".equals(Company.getCustomerZf())){
            Company.setCustomerZf(null);
        }
        if("".equals(Company.getCalled())){
            Company.setCalled(null);
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
        Example<Company> example = Example.of(Company/*,matcher*/);
        Page<Company> all = companyRepository.findAll(example,pageable);
        return all;
    }
    public Company saveOrUpdate(Company Company){
        return companyRepository.save(Company);
    }
    public Company getById(Long id){
        return companyRepository.getOne(id);
    }

    public List<Company> findAll(){
       return companyRepository.findAll();
    }
    public Company findByPhone(String phone){
        List<Company> list = companyRepository.findCompanyByPhone(phone);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Page<Company> fenpeiList(Integer pageNumber,Integer pageSize,Company Company){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(Company.getCustomerWx())){
            Company.setCustomerWx(null);
        }
        if("".equals(Company.getCustomerYx())){
            Company.setCustomerYx(null);
        }
        if("".equals(Company.getCustomerZf())){
            Company.setCustomerZf(null);
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
        if("是".equals(Company.getFen())){
            if(StringUtils.isEmpty(Company.getZy())){
                return  companyRepository.findByAndOptIdIsNull(pageable);
            }
            return companyRepository.findByAndOptIdIsNullAndZyContaining(Company.getZy(),pageable);
        }else if("否".equals(Company.getFen())){
            if(StringUtils.isEmpty(Company.getZy())){
                return companyRepository.findByAndOptIdIsNotNull(pageable);
            }
            return companyRepository.findByAndOptIdIsNotNullAndZyContaining(Company.getZy(),pageable);
        }
        return null;
    }
    public List<Company> findFirst10(){
        return companyRepository.findFirst10ByCustomerWx("是");
    }
    public List<Company> findExport(Company Company){
        if("".equals(Company.getCustomerWx())){
            Company.setCustomerWx(null);
        }
        if("".equals(Company.getCustomerYx())){
            Company.setCustomerYx(null);
        }
        if("".equals(Company.getCustomerZf())){
            Company.setCustomerZf(null);
        }
        if("".equals(Company.getOptName())){
            Company.setOptName(null);
        }
        Example<Company> example = Example.of(Company);
        List<Company> all = companyRepository.findAll(example);
        return all;
    }

    @Transactional
    public Integer repeatDelete(){
        return companyRepository.repeatDelete();
    }

    @Transactional
    public Integer fenPei(Long userId,String optName,Long[] ids){
        return companyRepository.fenPei(userId,optName,ids);
    }


}
