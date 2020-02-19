package com.agile.service.impl;

import com.agile.dao.OrderDao;
import com.agile.pojo.Order;
import com.agile.pojo.OrderItem;
import com.agile.pojo.example.OrderExample;
import com.agile.service.OrderItemService;
import com.agile.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao = null;

    @Autowired
    private OrderItemService orderItemService = null;

    @Override
    public Order get(int id) {
        return orderDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> listAll() {
        OrderExample example = new OrderExample();
        List<Order> orders = orderDao.selectByExample(example);
        setOrderItems(orders);
        return orders;
    }

    @Override
    public List<Order> list(Integer user_id, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.or().andUser_idEqualTo(user_id).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        List<Order> orders = orderDao.selectByExample(example);
        setOrderItems(orders);
        return orders;
    }

    @Override
    public List<Order> listByUserId(Integer user_id) {
        OrderExample example = new OrderExample();
        example.or().andUser_idEqualTo(user_id);
        List<Order> orders = orderDao.selectByExample(example);
        setOrderItems(orders);
        return orders;
    }

    public void setOrderItems(List<Order> orders) {
        for (Order order : orders) {
            setOrderItems(order);
        }
    }

    public void setOrderItems(Order order) {
        List<OrderItem> orderItems = orderItemService.getByOrderId(order.getId());
        order.setOrderItems(orderItems);
    }

    @Override
    public void update(Order order) {
        orderDao.updateByPrimaryKey(order);
    }

    @Override
    public void add(Order order) {
        orderDao.insert(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        add(order);

        //测试增加订单出现异常后，事务管理是否正常发生
        if(false){
            throw new RuntimeException();
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder_id(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPrice() * orderItem.getNumber();
        }
        return total;
    }
}
