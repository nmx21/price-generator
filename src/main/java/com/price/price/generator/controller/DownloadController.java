package com.price.price.generator.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import static com.price.price.generator.service.XLSXFileGenerator.generateXlsxFile;
import static com.price.price.generator.service.XLSXFileGenerator.generateXlsxFileByCategories;

@Controller
public class DownloadController {
    @GetMapping("/download")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Enumeration<String> paramNames = request.getParameterNames();
        String typeXLSXFile = "standart";
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.equalsIgnoreCase("type")) {
                typeXLSXFile = request.getParameter(paramName);
            }
        }

        Workbook wb;
        String fileName;
        switch (typeXLSXFile) {
            case "category" -> {
                wb = generateXlsxFileByCategories();
                fileName = "price-by-categories.xlsx";
            }
            default -> {
                wb = generateXlsxFile();
                fileName = "price.xlsx";
            }
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        wb.close();
        out.close();
    }

}
