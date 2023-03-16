package com.price.price.generator.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.OutputStream;

import static com.price.price.generator.service.XLSXFileGenerator.generateXlsxFile;

@Controller
public class DownloadController {
    @GetMapping("/download")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "price.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        Workbook wb = generateXlsxFile();
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        wb.close();
        out.close();
    }
}
