package com.price.price.generator.controller;

import com.price.price.generator.service.XLSXFileParcer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

import static com.price.price.generator.controller.Controller.ADD_VIEW;
import static com.price.price.generator.controller.Controller.LIST_VIEW;

@org.springframework.stereotype.Controller
public class UploadController {

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XLSXFileParcer.parceXLSXFile(workbook);
        } catch (IOException e) {

        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_VIEW);
        return modelAndView;
    }
}