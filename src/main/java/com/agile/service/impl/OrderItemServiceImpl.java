package com.agile.service.impl;

import com.agile.dao.OrderItemDao;
import com.agile.pojo.Order;
import com.agile.pojo.OrderItem;
import com.agile.pojo.Product;
import com.agile.pojo.example.OrderItemExample;
import com.agile.service.OrderItemService;
import com.agile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao = null;
    @Autowired
    private ProductService productService = null;

    @Override
    public OrderItem getById(Integer id) {
        OrderItem orderItem = orderItemDao.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public void add(OrderItem orderItem) {
        orderItemDao.insert(orderItem);
    }

    @Override
    public List<OrderItem> getByOrderId(Integer order_id) {
        OrderItemExample example = new OrderItemExample();
        example.or().andOrder_idEqualTo(order_id);
        List<OrderItem> orderItems = orderItemDao.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemDao.updateByPrimaryKey(orderItem);
    }

    @Override
    public List<OrderItem> listByUserId(Integer user_id) {
        OrderItemExample example = new OrderItemExample();
        example.or().andUser_idEqualTo(user_id);
        List<OrderItem> orderItems = orderItemDao.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    @Override
    public List<OrderItem> listForCart(Integer user_id) {
        OrderItemExample example = new OrderItemExample();
        example.or().andUser_idEqualTo(user_id).andOrder_idIsNull();
        List<OrderItem> orderItems = orderItemDao.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    public void setProduct(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setProduct(orderItem);
        }
    }

    public void setProduct(OrderItem orderItem) {
        Product product = productService.get(orderItem.getProduct_id());
        orderItem.setProduct(product);
    }

    @Override
    public void delete(Integer id) {
        orderItemDao.deleteByPrimaryKey(id);
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order o) {
        float total = 0;
        int totalNumber = 0;
        List<OrderItem> orderItems = o.getOrderItems();
        System.out.println(orderItems.size());
        for (OrderItem oi : orderItems) {
            System.out.println("number" + oi.getNumber());
            System.out.println("price" + oi.getProduct().getPrice());
            total += oi.getNumber() * oi.getProduct().getPrice();
            totalNumber += oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
    }
}
