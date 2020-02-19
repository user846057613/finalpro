package com.agile.controller;

import com.agile.pojo.Product;
import com.agile.pojo.PropertyValue;
import com.agile.pojo.Review;
import com.agile.service.ProductService;
import com.agile.service.PropertyValueService;
import com.agile.service.ReviewService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService = null;

    @Autowired
    private ReviewService reviewService = null;

    @Autowired
    private PropertyValueService propertyValueService = null;

    @RequestMapping("/searchProduct")
    public String search(Model model, String keyword) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        for (Product product : products) {
            product.setReviewCount(reviewService.getCount(product.getId()));
        }
        model.addAttribute("products", products);
        return"searchResult";
    }

    @RequestMapping("/showProduct")
    public String show(Model model, Integer product_id){
        Product product = productService.get(product_id);
        model.addAttribute("product", product);
        List<PropertyValue> propertyValues = propertyValueService.listByProductId(product_id);
        model.addAttribute("propertyValues", propertyValues);
        List<Review> reviews = reviewService.listByProductId(product_id);
        model.addAttribute("reviews", reviews);

        return "product";
    }

    @RequestMapping("/sortProduct")
    public String sortProduct(String sort,@RequestParam("keyword") String keyWord,Model model) {
        List<Product> products = productService.search(keyWord);
        for (Product product : products) {
            product.setReviewCount(reviewService.getCount(product.getId()));
        }
        if(sort != null) {
            switch (sort) {
                case "all":
                    Collections.sort(products, Comparator.comparing(Product::getSaleXReviewCount));
                    break;
                case "reviewCount":
                    Collections.sort(products, Comparator.comparing(Product::getReviewCount));
                    break;
                case "sale":
                    Collections.sort(products, Comparator.comparing(Product::getSale));
                    break;
                case"price":
                    Collections.sort(products, Comparator.comparing(Product::getPrice));
            }
        }
        model.addAttribute("products", products);
        return  "searchResult";
    }

}
