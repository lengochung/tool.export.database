package com.example.demo.mybatis.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.mybatis.model.TableDetail;
import com.example.demo.mybatis.model.TableEntity;


@Mapper
public interface TableMapper {

    @Select("SELECT * FROM information_schema.tables WHERE table_schema = 'flex_anphu' ORDER BY TABLE_NAME;") 
    List<TableEntity> getTables(); 

    @Select("SELECT\r\n" + //
                "\tCONCAT('[', GROUP_CONCAT(s.SEQ_IN_INDEX SEPARATOR ', '), ']') SEQ_IN_INDEX_JSON, c.*\r\n" + //
                "FROM\n" + //
                "\tINFORMATION_SCHEMA.COLUMNS c\r\n" + //
                "\tLEFT JOIN (\r\n" + //
                "\t\tSELECT * FROM information_schema.statistics s WHERE table_schema = 'flex_anphu' AND table_name = #{table_name}\r\n" + //
                "\t) s ON c.TABLE_NAME = s.TABLE_NAME AND c.COLUMN_NAME = s.COLUMN_NAME\n" + //
                "WHERE\n" + //
                "\tc.TABLE_SCHEMA = 'flex_anphu' \n" + //
                "\tAND c.TABLE_NAME = #{table_name} GROUP BY c.COLUMN_NAME ORDER BY IFNULL(c.COLUMN_KEY, 'Y') DESC, c.COLUMN_NAME ASC;")
    List<TableDetail> getTableDetail(@Param("table_name") String table_name);
}
