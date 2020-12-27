package com.example.demo.dao;

import com.example.demo.domain.table.User;
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
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);
    User findUserByNameAndPassword(String name,String password);
    List<User> findUserByName(String name);
    List<User> findUserById(Long id);
}
