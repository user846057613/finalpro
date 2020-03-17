package com.agile.service.impl;

import com.agile.dao.PropertyDao;
import com.agile.dao.PropertyValueDao;
import com.agile.pojo.Product;
import com.agile.pojo.Property;
import com.agile.pojo.PropertyValue;
import com.agile.pojo.example.PropertyValueExample;
import com.agile.service.PropertyService;
import com.agile.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    private PropertyValueDao propertyValueDao = null;
    @Autowired
    private PropertyService propertyService = null;

    @Override
    public void add(PropertyValue propertyValue) {
        propertyValueDao.insert(propertyValue);
    }

    @Override
    public void delete(Integer id) {
        propertyValueDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByProductId(Integer product_id) {
        List<PropertyValue> propertyValues = listByProductId(product_id);
        for (PropertyValue propertyValue : propertyValues) {
            propertyValueDao.deleteByPrimaryKey(propertyValue.getId());
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueDao.updateByPrimaryKey(propertyValue);
    }

    @Override
    public List<PropertyValue> list(Integer product_id, Integer category_id) {
        List<PropertyValue> propertyValues = new ArrayList<>();
        List<Property> properties = propertyService.list(category_id);
        for (Property property : properties) {
            PropertyValueExample example = new PropertyValueExample();
            example.or().andProperty_idEqualTo(property.getId()).andProduct_idEqualTo(product_id);
            List<PropertyValue> propertyValueList = propertyValueDao.selectByExample(example);
            for (PropertyValue propertyValue : propertyValueList) {
                propertyValue.setProperty(property);
            }
            propertyValues.addAll(propertyValueList);
        }
        return propertyValues;
    }

    @Override
    public PropertyValue get(Integer id) {
        PropertyValue propertyValue = propertyValueDao.selectByPrimaryKey(id);
        setProperty(propertyValue);
        return propertyValue;
    }

    @Override
    public List<PropertyValue> listByProductId(Integer product_id) {
        PropertyValueExample example = new PropertyValueExample();
        example.or().andProduct_idEqualTo(product_id);
        List<PropertyValue> propertyValues = propertyValueDao.selectByExample(example);
        setProperty(propertyValues);
        return propertyValues;
    }

    @Override
    public void fill(List<Product> products) {
        for (Product product : products) {
            fill(product);
        }
    }

    @Override
    public void fill(Product product) {
        List<PropertyValue> list = this.listByProductId(product.getId());
        product.setPropertyValues(list);
    }

    public void setProperty(List<PropertyValue> propertyValues) {
        for (PropertyValue propertyValue : propertyValues) {
            setProperty(propertyValue);
        }
    }

    public void setProperty(PropertyValue property) {
        property.setProperty(propertyService.get(property.getProperty_id()));
    }
}
