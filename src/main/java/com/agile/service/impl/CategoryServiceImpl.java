package com.agile.service.impl;

import com.agile.dao.CategoryDao;
import com.agile.pojo.Category;
import com.agile.pojo.example.CategoryExample;
import com.agile.service.CategoryService;
import com.agile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao = null;

    @Autowired
    private ProductService productService = null;
    @Override
    public List<Category> list() {
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categories = categoryDao.selectByExample(categoryExample);
        productService.fill(categories);
        productService.fillByRow(categories);
        return categories;
    }

    @Override
    public Category get(Integer id) {
        Category category = categoryDao.selectByPrimaryKey(id);
        productService.fill(category);
        return category;
    }

    @Override
    public void update(Category category) {
        categoryDao.updateByPrimaryKey(category);
    }
}
