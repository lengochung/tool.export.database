package com.example.demo.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mybatis.model.TableEntity;
import com.example.demo.mybatis.service.TableService;
import com.example.demo.service.ExcelExportService;

@RestController
public class HelloController {

    @Autowired
    ExcelExportService excelExportService;

    @Autowired
    TableService tableService;

    @GetMapping("/run")
    public String export() throws IOException {     
        this.excelExportService.exportDataToExcel(); 
        return "DONE!";
    }
}