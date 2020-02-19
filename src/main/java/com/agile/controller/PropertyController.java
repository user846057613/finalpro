package com.agile.controller;

import com.agile.pojo.Category;
import com.agile.pojo.Property;
import com.agile.service.CategoryService;
import com.agile.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PropertyController {

    @Autowired
    private PropertyService propertyService = null;

    @Autowired
    private CategoryService categoryService = null;

    @RequestMapping("/listProperty")
    public String list(Model model, Integer category_id) {
        List<Property> properties = propertyService.list(category_id);
        model.addAttribute("properties", properties);
        Category category = categoryService.get(category_id);
        model.addAttribute("category", category);
        return "admin/listProperty";
    }
    @RequestMapping("/addProperty")
    public String add(Model model, Integer category_id, Property property) {
        propertyService.add(property);
        return "redirect: listProperty?category_id=" + category_id;
    }

    @RequestMapping("/editProperty")
    public String edit(Model model, Integer id) {
        Property property = propertyService.get(id);
        model.addAttribute("property",property);
        return "admin/editProperty";
    }
    @RequestMapping("/updateProperty")
    public String update(Property property) {
        propertyService.update(property);
        System.out.println(property.getCategory_id());
        return "redirect: listProperty?category_id=" + property.getCategory_id();
    }
}
