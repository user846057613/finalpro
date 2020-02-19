package com.agile.controller;

import com.agile.pojo.Category;
import com.agile.pojo.Product;
import com.agile.pojo.ProductImage;
import com.agile.service.CategoryService;
import com.agile.service.ProductImageService;
import com.agile.service.ProductService;
import com.agile.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService = null;

    @Autowired
    private CategoryService categoryService = null;

    @Autowired
    private ProductImageService productImageService = null;

    @Autowired
    private PropertyValueService propertyValueService = null;

    @RequestMapping("/listProduct")
    public String list(Model model, Integer category_id) {
        List<Product> products = productService.list(category_id);
        model.addAttribute("products", products);
        Category category = categoryService.get(category_id);
        model.addAttribute("category", category);
        return "admin/listProduct";
    }

    @RequestMapping("/editProduct")
    public String edit(Model model, Integer id) {
        Product product = productService.get(id);
        model.addAttribute("product", product);
        Category category = categoryService.get(product.getCategory_id());
        model.addAttribute("category", category);
        return "admin/editProduct";
    }

    @RequestMapping("/updateProduct")
    public String update(Model model, Product product) {
        productService.update(product);
        return "redirect: listProduct?category_id=" + product.getCategory_id();
    }

    @RequestMapping("/addProduct")
    public String add(Model model, Product product) {
        productService.add(product);

        ProductImage productImage = new ProductImage();
        productImage.setProduct_id(product.getId());
        for (int i = 0; i < 5; i++) {
            productImageService.add(productImage);
        }
        return "redirect:listProduct?category_id=" + product.getCategory_id();
    }

    @RequestMapping("/deleteProduct")
    public String delete(Model model, Integer id, HttpServletRequest request) {
        productImageService.deleteByProductId(id);

        String path = request.getSession().getServletContext().getRealPath("" + id);
        deleteDir(new File(path));

        propertyValueService.deleteByProductId(id);

        int category_id =productService.get(id).getCategory_id();
        productService.delete(id);

        return "redirect: listProduct?category_id=" + category_id;
    }

    private static boolean deleteDir(File dir) {
        if(dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if(!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
