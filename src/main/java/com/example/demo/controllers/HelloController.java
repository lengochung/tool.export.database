package com.example.demo.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ExcelExportService;

@RestController
public class HelloController {

    @Autowired
    ExcelExportService excelExportService;
    @GetMapping("/run")
    public String export() throws IOException {
           
           
        this.excelExportService.exportDataToExcel();
 
        return "redirect:/run";
    }

}