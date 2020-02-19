package com.agile.controller;

import com.agile.pojo.Product;
import com.agile.pojo.ProductImage;
import com.agile.service.ProductImageService;
import com.agile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService = null;

    @Autowired
    private ProductService productService = null;

    @RequestMapping("/editProductImage")
    public String edit(Model model, Integer product_id) {
        List<ProductImage> list = productImageService.list(product_id);
        model.addAttribute("productImages", list);
        Product product = productService.get(product_id);
        model.addAttribute("product", product);
        return "admin/editProductImage";
    }
}
