package com.agile.controller;

import com.agile.pojo.Order;
import com.agile.pojo.OrderItem;
import com.agile.pojo.Product;
import com.agile.pojo.User;
import com.agile.service.OrderItemService;
import com.agile.service.OrderService;
import com.agile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private OrderService orderService = null;

    @Autowired
    private OrderItemService orderItemService = null;

    @Autowired
    private ProductService productService = null;

    @RequestMapping("/addCart")
    @ResponseBody
    public String addCart(Integer product_id, Integer number, @SessionAttribute("user")User user) {
        Product product = productService.get(product_id);
        int orderItemId = 0;
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUserId(user.getId());
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getProduct_id() == product_id && orderItem.getOrder_id() == null) {
                orderItem.setNumber(orderItem.getNumber() + number);
                orderItemService.update(orderItem);
                found = true;
                break;
            }
        }
        if(!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setNumber(number);
            orderItem.setProduct_id(product_id);
            orderItem.setUser_id(user.getId());
            orderItemService.add(orderItem);
        }

        return "success";
    }

    @RequestMapping("/cart")
    public String cart(Model model, @SessionAttribute("user") User user) {
        List<OrderItem> orderItems = orderItemService.listForCart(user.getId());
        model.addAttribute("orderItems", orderItems);
        return "cart";
    }

    @RequestMapping("/deleteOrderItem")
    public String deleteOrderItem(Integer orderItemId, @SessionAttribute("user")User user) {
        if(user == null) {
            return "loginPage";
        }
        orderItemService.delete(orderItemId);
        return "redirect:cart";
    }
}
