package com.example.demo.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.mybatis.model.TableDetail;
import com.example.demo.mybatis.model.TableEntity;


@Mapper
public interface TableMapper {

    // @Select("SELECT * FROM information_schema.tables WHERE table_schema = 'flex_anphu' ORDER BY TABLE_NAME;") 
    List<TableEntity> getTables(); 

    // @Select("SELECT\r\n" + //
    //             "\tGROUP_CONCAT(t.TABLE_NAME SEPARATOR '\n') APPEAR_ON," + 
    //             "\tCONCAT('[', GROUP_CONCAT(s.SEQ_IN_INDEX SEPARATOR ', '), ']') SEQ_IN_INDEX_JSON, c.*\r\n" + //
    //             "FROM\n" + //
    //             "\tINFORMATION_SCHEMA.COLUMNS c\r\n" + //
    //             "\tLEFT JOIN (\r\n" + //
    //             "\t\tSELECT * FROM information_schema.statistics s WHERE table_schema = 'flex_anphu' AND table_name = #{table_name}\r\n" + //
    //             "\t) s ON c.TABLE_NAME = s.TABLE_NAME AND c.COLUMN_NAME = s.COLUMN_NAME\n" + //
    //             "LEFT JOIN ( SELECT * FROM information_schema.COLUMNS s WHERE table_schema = 'flex_anphu') t ON c.COLUMN_NAME = t.COLUMN_NAME AND c.TABLE_NAME != t.TABLE_NAME\n" +
    //             "WHERE\n" + //
    //             "\tc.TABLE_SCHEMA = 'flex_anphu' \n" + //
    //             "\tAND c.TABLE_NAME = #{table_name} GROUP BY c.COLUMN_NAME ORDER BY IFNULL(c.COLUMN_KEY, 'Y') DESC, c.COLUMN_NAME ASC;")
    List<TableDetail> getTableDetail(TableEntity table);
}
