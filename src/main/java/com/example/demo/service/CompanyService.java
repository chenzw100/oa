package com.example.demo.service;

import com.example.demo.dao.CompanyRepository;
import com.example.demo.domain.table.Company;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Component
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    public void delete(Company Company){
        companyRepository.delete(Company);
    }
    public Page<Company> findALl(Integer pageNumber,Integer pageSize,Company company){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(company.getOptName())){
            company.setOptName(null);
        }
        if("".equals(company.getCustomerWx())){
            company.setCustomerWx(null);
        }
        if("".equals(company.getCustomerYx())){
            company.setCustomerYx(null);
        }
        if("".equals(company.getCustomerZf())){
            company.setCustomerZf(null);
        }
        if("".equals(company.getCalled())){
            company.setCalled(null);
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
        Example<Company> example = Example.of(company/*,matcher*/);
        Page<Company> all =null;
        if(company.getModifiedStart()!=null){
            if(company.getModifiedEnd()==null){
                company.setModifiedEnd(new Date());
            }else {
                long time =company.getModifiedEnd().getTime()+24*60*60*1000-1;
                company.setModifiedEnd(new Date(time));
            }
            if(company.getCalled()==null){
                all = companyRepository.findByModifiedBetween(company.getModifiedStart(),company.getModifiedEnd(),pageable);
            }else {
                all = companyRepository.findByModifiedBetweenAndCalled(company.getModifiedStart(),company.getModifiedEnd(),company.getCalled(),pageable);
            }
        }else {
            all = companyRepository.findAll(example,pageable);
        }

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

    public Page<Company> fenpeiList(Integer pageNumber,Integer pageSize,Company company){
        if(pageNumber==null || pageNumber<0){
            pageNumber=1;
            pageSize=10;
        }
        pageNumber--;
        if("".equals(company.getCustomerWx())){
            company.setCustomerWx(null);
        }
        if("".equals(company.getCustomerYx())){
            company.setCustomerYx(null);
        }
        if("".equals(company.getCustomerZf())){
            company.setCustomerZf(null);
        }
        if("".equals(company.getOptName())){
            company.setOptName(null);
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
        if("是".equals(company.getFen())){
            if(StringUtils.isEmpty(company.getZy())){
                return  companyRepository.findByAndOptIdIsNull(pageable);
            }
            return companyRepository.findByAndOptIdIsNullAndZyContaining(company.getZy(),pageable);
        }else if("否".equals(company.getFen())){
            if(StringUtils.isEmpty(company.getZy())){
                if(StringUtils.isEmpty(company.getOptName())){
                    return companyRepository.findByAndOptIdIsNotNull(pageable);
                }
                return companyRepository.findByOptName(company.getOptName(),pageable);
            }
            return companyRepository.findByAndOptIdIsNotNullAndZyContaining(company.getZy(),pageable);
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
