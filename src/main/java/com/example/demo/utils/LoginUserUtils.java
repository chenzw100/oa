package com.example.demo.utils;

import com.example.demo.domain.table.User;

import java.util.concurrent.ConcurrentHashMap;

public class LoginUserUtils {
    public static ConcurrentHashMap loginUserMap = new ConcurrentHashMap();
    public static void putUser(User user){
        loginUserMap.put(user.getId(),user.getName());
    }
    public static void clearUser(User user){
        loginUserMap.remove(user.getId());
    }
    public static void getUser(long id){
        loginUserMap.remove(id);
    }
}
