package com.price.price.generator.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import static com.price.price.generator.controller.Controller.fileName;
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
        String partFileName;
        if (typeXLSXFile.equals("category")) {
            wb = generateXlsxFileByCategories();
            partFileName = "_by_categories_";
        } else if (typeXLSXFile.equals("page")) {
            wb = generateXlsxFileByCategoriesInSeparateSheets();
            partFileName = "_by_page_";
        } else {
            wb = generateXlsxFile();
            partFileName = "_price_";
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String encodedFileName = URLEncoder.encode(fileName + partFileName + getDateTimeForName() + ".xlsx", StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        wb.close();
        out.close();
    }

    private String getDateTimeForName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
        return currentDateTime.format(formatter);
    }

}
