package com.price.price.generator.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import static com.price.price.generator.service.XLSXFileGenerator.*;

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
            if (paramName.equalsIgnoreCase("page")) {
                typeXLSXFile = request.getParameter(paramName);
            }
        }

        Workbook wb;
        String fileName;
        if (typeXLSXFile.equals("category")) {
            wb = generateXlsxFileByCategories();
            fileName = "price-by-categories.xlsx";
        } else if(typeXLSXFile.equals("page")){
            wb = generateXlsxFileByCategoriesInSeparateSheets();
            fileName = "price-by-page.xlsx";
        }
        else{
            wb = generateXlsxFile();
            fileName = "price.xlsx";
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
