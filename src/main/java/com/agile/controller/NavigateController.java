package com.agile.controller;

import com.agile.pojo.Category;
import com.agile.pojo.ReferalLink;
import com.agile.service.CategoryService;
import com.agile.service.ProductService;
import com.agile.service.ReferalLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NavigateController {
    @Autowired
    private CategoryService categoryService = null;
    @Autowired
    private ProductService productService = null;
    @Autowired
    private ReferalLinkService referalLinkService = null;

    @RequestMapping("/home")
    public String home(Model model) {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        List<ReferalLink> links = referalLinkService.listAll();

        model.addAttribute("categories", categories);
        model.addAttribute("links", links);
        return "index";
    }

    @RequestMapping("/loginPage")
    public String tologinPage() {
        return "loginPage";
    }

    @RequestMapping("/registerPage")
    public String toRegisterPage() {
        return "registerPage";
    }

    @RequestMapping("/registerSuccessPage")
    public String toRegisterSuccessPage(){
        return "registerSuccessPage";
    }

    @RequestMapping("/payPage")
    public String toPayPage(){
        return "alipay";
    }
}
