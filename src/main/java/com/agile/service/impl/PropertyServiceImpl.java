package com.agile.service.impl;

import com.agile.dao.PropertyDao;
import com.agile.pojo.Property;
import com.agile.pojo.example.PropertyExample;
import com.agile.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyDao propertyDao = null;

    @Override
    public void add(Property property) {
        propertyDao.insert(property);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Property property) {
        propertyDao.updateByPrimaryKey(property);
    }

    @Override
    public List<Property> list(Integer category_id) {
        PropertyExample example = new PropertyExample();
        example.or().andCategory_idEqualTo(category_id);
        return propertyDao.selectByExample(example);
    }

    @Override
    public Property get(Integer id) {
        return propertyDao.selectByPrimaryKey(id);
    }
}
