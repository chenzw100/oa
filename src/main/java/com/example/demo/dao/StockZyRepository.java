package com.example.demo.dao;

import com.example.demo.domain.table.StockZy;
import com.example.demo.domain.table.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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



}
