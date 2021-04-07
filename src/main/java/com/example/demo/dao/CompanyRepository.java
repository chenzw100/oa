package com.example.demo.dao;

import com.example.demo.domain.table.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
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
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company save(Company Company);

    List<Company> findCompanyByPhone(String phone);
    Page<Company> findByAndOptIdIsNull(Pageable pageable);
    Page<Company> findByAndOptIdIsNotNull(Pageable pageable);
    Page<Company> findByOptName(String optName, Pageable pageable);
    Page<Company> findByAndOptIdIsNullAndZyContaining(String zy, Pageable pageable);
    Page<Company> findByAndOptIdIsNotNullAndZyContaining(String zy, Pageable pageable);
    List<Company> findFirst10ByCustomerWx(String wx);
    Page<Company> findByModifiedBetween(Date start, Date end, Pageable pageable);
    Page<Company> findByModifiedBetweenAndCalled(Date start, Date end,String called, Pageable pageable);
    @Override
    void delete(Company Company);
    @Modifying
    @Query(value=" DELETE FROM company WHERE id in(SELECT id FROM (select id,phone,count(*) as count from company group by phone having count>1) temp)", nativeQuery = true)
    public Integer repeatDelete();
    @Modifying
    @Query(value=" UPDATE company set opt_id=?1,opt_name=?2,fen_date=now(),modified=now() WHERE id in ?3 ", nativeQuery = true)
    public Integer fenPei(Long optId, String optName, Long[] ids);

    @Modifying
    @Query(value=" UPDATE company set opt_id=null,opt_name=null,fen_date=now()  WHERE modified <?1 and modified!=fen_date and opt_id>0", nativeQuery = true)
    public Integer resetFP(Date modified);
}
