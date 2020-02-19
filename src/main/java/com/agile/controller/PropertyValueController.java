package com.agile.controller;

import com.agile.pojo.Product;
import com.agile.pojo.Property;
import com.agile.pojo.PropertyValue;
import com.agile.service.ProductService;
import com.agile.service.PropertyService;
import com.agile.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService = null;
    @Autowired
    private ProductService productService = null;
    @Autowired
    private PropertyService propertyService = null;

    @RequestMapping("/listPropertyValue")
    public String list(Model model, Integer product_id, Integer category_id) {
        List<PropertyValue> list = propertyValueService.list(product_id,category_id);
        model.addAttribute("propertyValues", list);
        Product product = productService.get(product_id);
        model.addAttribute("product", product);
        List<Property> properties = propertyService.list(category_id);
        model.addAttribute("properties", properties);
        return "admin/listPropertyValue";
    }

    @RequestMapping("/editPropertyValue")
    public String edit(Model model, Integer id) {
        PropertyValue propertyValue = propertyValueService.get(id);
        model.addAttribute("propertyValue", propertyValue);
        Product product = productService.get(propertyValue.getProduct_id());
        model.addAttribute("product", product);
        return"admin/editPropertyValue";
    }

    @RequestMapping("/updatePropertyValue")
    public String update(PropertyValue propertyValue) {
        Integer product_id = propertyValue.getProduct_id();
        Integer category_id = productService.get(product_id).getCategory_id();
        propertyValueService.update(propertyValue);
        return "redirect: listPropertyValue?product_id=" + product_id + "&category_id=" + category_id;
    }

    @RequestMapping("/deletePropertyValue")
    public String delete(Integer id, Integer category_id) {
        int product_id = propertyValueService.get(id).getProduct_id();
        propertyValueService.delete(id);
        return "redirect: listPropertyValue?product_id=" + product_id + "&category_id=" + category_id;
    }

    @RequestMapping("/addPropertyValue")
    public String add(PropertyValue propertyValue, Integer property_id) {
        Property property = propertyService.get(property_id);
        propertyValue.setProperty(property);
        propertyValueService.add(propertyValue);
        int product_id = propertyValue.getProduct_id();
        int category_id = propertyValue.getProperty().getCategory_id();
        return "redirect: listPropertyValue?product_id=" + product_id + "&category_id=" + category_id;
    }

}
