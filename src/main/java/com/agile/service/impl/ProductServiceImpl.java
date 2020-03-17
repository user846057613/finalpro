package com.agile.service.impl;

import com.agile.dao.ProductDao;
import com.agile.pojo.Category;
import com.agile.pojo.Product;
import com.agile.pojo.example.ProductExample;
import com.agile.service.ProductImageService;
import com.agile.service.ProductService;
import com.agile.service.PropertyValueService;
import com.agile.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao = null;

    @Autowired
    private ReviewService reviewService = null;

    @Autowired
    private ProductImageService productImageService = null;

    @Autowired
    private PropertyValueService propertyValueService = null;

    @Override
    public void add(Product product) {
        productDao.insert(product);
    }

    @Override
    public void delete(Integer id) {
        productDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productDao.updateByPrimaryKey(product);
    }

    @Override
    public Product get(Integer id) {
        Product product = productDao.selectByPrimaryKey(id);
        product.setReviewCount(reviewService.getCount(id));
        productImageService.fill(product);
        propertyValueService.fill(product);
        return product;
    }

    @Override
    public List<Product> list(Integer category_id) {
        ProductExample example = new ProductExample();
        example.or().andCategory_idEqualTo(category_id);
        List<Product> products = productDao.selectByExample(example);
        productImageService.fill(products);
        propertyValueService.fill(products);
        return products;
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberOfEachRow = 8;
        for (Category category : categories) {
            List<Product>products = category.getProducts();
            List< List<Product>> productByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i+= productNumberOfEachRow) {
                int size = i + productNumberOfEachRow;
                size =size > products.size() ? products.size():size;
                List<Product> productsEachRow = products.subList(i, size);
                productByRow.add(productsEachRow);
            }
            category.setProductByRow(productByRow);
        }
    }

    @Override
    public void setReviewCount(Product product) {
        int count = reviewService.getCount(product.getId());
        product.setReviewCount(count);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.or().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List<Product> products = productDao.selectByExample(example);
        productImageService.fill(products);
        propertyValueService.fill(products);
        return products;
    }
}
