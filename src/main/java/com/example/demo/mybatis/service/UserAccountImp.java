package com.example.demo.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mybatis.mapper.UserAccountMapper;
import com.example.demo.mybatis.model.UserAccountEntity;

@Service
public class UserAccountImp implements UserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Override
    public List<UserAccountEntity> findAll() {
        // TODO Auto-generated method stub
        return userAccountMapper.findAll();
    }

    @Override
    public List<UserAccountEntity> all() {
        // TODO Auto-generated method stub
        return userAccountMapper.all();
    }
    
}
