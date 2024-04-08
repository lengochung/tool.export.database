package com.example.demo.mybatis.service;

import java.util.List;

import com.example.demo.mybatis.model.UserAccountEntity;

public interface UserAccountService {
    
    List<UserAccountEntity> findAll();
    List<UserAccountEntity> all();
}
