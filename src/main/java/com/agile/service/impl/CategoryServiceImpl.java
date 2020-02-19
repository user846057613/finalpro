package com.agile.service.impl;

import com.agile.dao.CategoryDao;
import com.agile.pojo.Category;
import com.agile.pojo.example.CategoryExample;
import com.agile.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao = null;
    @Override
    public List<Category> list() {
        CategoryExample categoryExample = new CategoryExample();
        return categoryDao.selectByExample(categoryExample);
    }

    @Override
    public Category get(Integer id) {
        return categoryDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryDao.updateByPrimaryKey(category);
    }
}
