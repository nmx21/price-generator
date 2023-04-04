package com.price.price.generator.controller;

import com.price.price.generator.model.Goods;
import com.price.price.generator.model.PlainText;
import com.price.price.generator.service.PriceGeneratorService;
import com.price.price.generator.service.PriceTableGenerator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    public static final String INDEX_VIEW = "index";
    public static final String ANALYSIS_VIEW = "analysis";
    public static final String ADD_VIEW = "add";
    public static final String LIST_VIEW = "list";
    public static List<String> badList = new ArrayList<>();
    public static List<String> withOutPriceList = new ArrayList<>();
    public static List<Goods> goodsList = new ArrayList<>();
    public static String fileName = "price";

    @GetMapping("/")
    public ModelAndView homePage() {
        goodsList = new ArrayList<>();
        badList = new ArrayList<>();
        withOutPriceList = new ArrayList<>();
        fileName = "noName";
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(INDEX_VIEW);
        return modelAndView;
    }

    @PostMapping("/start")
    public synchronized ModelAndView start(PlainText plainText, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        goodsList = PriceGeneratorService.prepareBasePrice(plainText);
        if (goodsList == null || goodsList.isEmpty()) {
            return new ModelAndView(new RedirectView("/"));
        } else {
            modelAndView.addObject("list", PriceTableGenerator.getTable());
            modelAndView.setViewName(LIST_VIEW);
            return modelAndView;
        }
    }

    @GetMapping("/start")
    public ModelAndView startGet() {
        if (goodsList == null || goodsList.isEmpty()) {
            return new ModelAndView(new RedirectView("/"));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("list", PriceTableGenerator.getTable());
            modelAndView.setViewName(LIST_VIEW);
            return modelAndView;
        }
    }

    @GetMapping("/add")
    public ModelAndView addForm() {
        if (goodsList == null || goodsList.isEmpty()) {
            return new ModelAndView(new RedirectView("/"));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(ADD_VIEW);
            return modelAndView;
        }
    }

    @PostMapping("/analysis")
    public ModelAndView analysis(PlainText plainText) {
        if (goodsList == null || goodsList.isEmpty()) {
            return new ModelAndView(new RedirectView("/"));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            PriceGeneratorService.addNewPrice(plainText);
            modelAndView.addObject("list", PriceTableGenerator.getTable());
            modelAndView.addObject("badlist", badList);
            modelAndView.addObject("withOutPriceList", withOutPriceList);
            modelAndView.setViewName(ANALYSIS_VIEW);
            return modelAndView;
        }
    }

    @GetMapping("/analysis")
    public ModelAndView analysisGet() {
        if (goodsList == null || goodsList.isEmpty()) {
            return new ModelAndView(new RedirectView("/"));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("list", PriceTableGenerator.getTable());
            modelAndView.setViewName(ANALYSIS_VIEW);
            return modelAndView;
        }
    }

}
