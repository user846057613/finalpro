package com.agile.service.impl;

import com.agile.dao.ProductImageDao;
import com.agile.pojo.ProductImage;
import com.agile.pojo.example.ProductImageExample;
import com.agile.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageDao productImageDao = null;

    @Override
    public void add(ProductImage image) {
        productImageDao.insert(image);
    }

    @Override
    public void deleteByProductId(Integer product_id) {
        ProductImageExample example = new ProductImageExample();
        example.or().andProduct_idEqualTo(product_id);
        List<ProductImage> idList = productImageDao.selectByExample(example);
        for (ProductImage productImage : idList) {
            productImageDao.deleteByPrimaryKey(productImage.getId());
        }
    }

    @Override
    public void update(ProductImage image) {
        productImageDao.updateByPrimaryKey(image);
    }

    @Override
    public ProductImage get(Integer id) {
        return productImageDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductImage> list(Integer product_id) {
        ProductImageExample example = new ProductImageExample();
        example.or().andIdEqualTo(product_id);
        return productImageDao.selectByExample(example);
    }
}
