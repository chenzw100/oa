package com.example.demo.dao;

import com.example.demo.domain.table.StockZy;
import com.example.demo.domain.table.User;
import org.springframework.data.domain.Example;
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
public interface StockZyRepository extends JpaRepository<StockZy,Long> {
    StockZy save(StockZy stockZy);

    List<StockZy> findStockZyByPhone(String phone);
    Page<StockZy> findByAndOptIdIsNull(Pageable pageable);
    Page<StockZy> findByAndOptIdIsNotNull(Pageable pageable);
    Page<StockZy> findByOptName(String optName,Pageable pageable);
    Page<StockZy> findByAndOptIdIsNullAndZyContaining(String zy,Pageable pageable);
    Page<StockZy> findByAndOptIdIsNotNullAndZyContaining(String zy,Pageable pageable);
    List<StockZy> findFirst10ByCustomerWx(String wx);
    Page<StockZy> findByModifiedBetween(Date start, Date end, Pageable pageable);
    Page<StockZy> findByModifiedBetweenAndCalled(Date start, Date end,String called, Pageable pageable);
    @Override
    void delete(StockZy stockZy);
    @Modifying
    @Query(value=" DELETE FROM stock_zy WHERE id in(SELECT id FROM (select id,phone,count(*) as count from stock_zy group by phone having count>1) temp)", nativeQuery = true)
    public Integer repeatDelete();
    @Modifying
    @Query(value=" UPDATE stock_zy set opt_id=?1,opt_name=?2,fen_date=now(),modified=now() WHERE id in ?3 ", nativeQuery = true)
    public Integer fenPei(Long optId, String optName,Long[] ids);

    @Modifying
    @Query(value=" UPDATE stock_zy set opt_id=null,opt_name=null,fen_date=now()  WHERE modified <?1 and modified!=fen_date and opt_id>0", nativeQuery = true)
    public Integer resetFP(Date modified);
}
