package com.example.demo.dao;

import com.example.demo.domain.table.CompanyZy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**

 */
public interface CompanyZyRepository extends JpaRepository<CompanyZy,Long> {
    CompanyZy save(CompanyZy CompanyZy);

    List<CompanyZy> findCompanyZyByPhoneOne(String phone);
    Page<CompanyZy> findByAndOptIdIsNull(Pageable pageable);
    Page<CompanyZy> findByAndOptIdIsNotNull(Pageable pageable);
    Page<CompanyZy> findByAndOptIdIsNullAndZyContaining(String zy, Pageable pageable);
    Page<CompanyZy> findByAndOptIdIsNotNullAndZyContaining(String zy, Pageable pageable);
    List<CompanyZy> findFirst10ByCustomerWx(String wx);
    @Override
    void delete(CompanyZy CompanyZy);
    @Modifying
    @Query(value=" DELETE FROM company_zy WHERE id in(SELECT id FROM (select id,phone,count(*) as count from company_zy group by phone having count>1) temp)", nativeQuery = true)
    public Integer repeatDelete();
    @Modifying
    @Query(value=" UPDATE company_zy set opt_id=?1,opt_name=?2,fen_date=now() WHERE id in ?3 ", nativeQuery = true)
    public Integer fenPei(Long optId, String optName, Long[] ids);
}
