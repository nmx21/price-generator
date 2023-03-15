package com.price.price.generator.controller;

import com.price.price.generator.model.Goods;
import com.price.price.generator.model.Plain;
import com.price.price.generator.service.BasePriceGeneratorService;
import com.price.price.generator.service.PriceTableGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.price.price.generator.service.XLSXFileGenerator.generateXlsxFile;

@org.springframework.stereotype.Controller
public class Controller {
    public static List<Goods> goodsList;
    public static List<String> badList;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/start")
    public ModelAndView start(Plain plain, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        goodsList = BasePriceGeneratorService.prepareBasePrice(plain);
        modelAndView.addObject("list", PriceTableGenerator.getTable());
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @GetMapping("/start")
    public ModelAndView startGet(Plain plain, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", PriceTableGenerator.getTable());
        modelAndView.setViewName("list");
        if (goodsList == null) {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add");
        if (goodsList == null) {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @PostMapping("/analysis")
    public ModelAndView analysis(Plain plain, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        BasePriceGeneratorService.addNewPrice(plain);
        modelAndView.addObject("list", PriceTableGenerator.getTable());
        modelAndView.addObject("badlist", badList);
        modelAndView.setViewName("analysis");
        return modelAndView;
    }

    @GetMapping("/download")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "price.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        Workbook wb = generateXlsxFile();
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        wb.close();
        out.close();
    }


}
