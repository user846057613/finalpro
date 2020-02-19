package com.agile.dao;

import com.agile.pojo.ProductImage;
import com.agile.pojo.example.ProductImageExample;

import java.util.List;

public interface ProductImageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}
