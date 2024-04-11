package com.example.demo.mybatis.service;

import java.util.List;
import java.util.Set;

import com.example.demo.mybatis.model.TableDetail;
import com.example.demo.mybatis.model.TableEntity;

public interface TableService {
    List<TableEntity> getTables(); 
    List<TableDetail> getTableDetail(TableEntity table);
}
