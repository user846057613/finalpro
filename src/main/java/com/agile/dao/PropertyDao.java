package com.agile.dao;

import com.agile.pojo.Property;
import com.agile.pojo.example.PropertyExample;

import java.util.List;

public interface PropertyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}
