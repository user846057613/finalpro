package com.agile.dao;

import com.agile.pojo.PropertyValue;
import com.agile.pojo.example.PropertyValueExample;

import java.util.List;

public interface PropertyValueDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}
