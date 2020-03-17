package com.agile.controller;

import com.agile.pojo.Product;
import com.agile.pojo.ProductImage;
import com.agile.service.ProductImageService;
import com.agile.service.ProductService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

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

    @RequestMapping("/updateProductImage")
    public String update(HttpServletRequest request, Model model, ProductImage productImage, @RequestParam("picture") MultipartFile file){
        System.out.println("im coming");
        if(file != null) {
            String fileName = file.getOriginalFilename();
            String filePath = request.getSession().getServletContext().getRealPath("/uploads") + "\\"
                    +productImage.getProduct_id();
            System.out.println(filePath);
            File pFile = new File(filePath);
            if(!pFile.isDirectory()){
                pFile.mkdir();
            }
            String s = UUID.randomUUID() + fileName;
            fileName = filePath + "\\" + s;
            String fileName2 = "uploads" + "\\" + productImage.getProduct_id() + "\\" + s;
            try {
                file.transferTo(new File(fileName));
                productImage.setSrc(fileName2);
                productImageService.update(productImage);
            }catch (IllegalStateException | IOException e) {
                model.addAttribute("msg","上传文件失败");
                e.printStackTrace();
            }
        }
        return "redirect: editProductImage?product_id=" + productImage.getProduct_id();
    }

    @RequestMapping("/getImage")
    public void getImage(@RequestParam("path") String path) {

    }
}
