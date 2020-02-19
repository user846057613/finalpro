package com.agile.controller;

import com.agile.pojo.Order;
import com.agile.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {

    @Autowired
    private OrderService orderService = null;

    @RequestMapping("/listOrder")
    public String list(Model model) {
        List<Order> orders = orderService.listAll();
        model.addAttribute("orders", orders);
        return "admin/listOrder";
    }

    @RequestMapping("/orderDelivery")
    public String delivery(Integer order_id) {
        Order order = orderService.get(order_id);
        order.setDelivery_date(new Date());
        order.setStatus(orderService.waitConfirm);
        orderService.update(order);
        return "redirect:listOrder";
    }
}
