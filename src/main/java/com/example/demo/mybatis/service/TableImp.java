package com.example.demo.mybatis.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mybatis.mapper.TableMapper;
import com.example.demo.mybatis.model.TableDetail;
import com.example.demo.mybatis.model.TableEntity;

@Service
public class TableImp implements TableService {
    @Autowired
    TableMapper tableMapper;
    @Override
    public List<TableEntity> getTables() {
        // TODO Auto-generated method stub
        return this.tableMapper.getTables();
    }
    @Override
    public List<TableDetail> getTableDetail(String table_name) {
        // TODO Auto-generated method stub
        return this.tableMapper.getTableDetail(table_name);
    }
    
}
