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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BuyController {
    @Autowired
    private OrderService orderService = null;

    @Autowired
    private OrderItemService orderItemService = null;

    @Autowired
    private ProductService productService = null;

    @RequestMapping("/bought")
    public String bought(Model model, @SessionAttribute("user")User user) {
        List<Order> orders = orderService.list(user.getId(), OrderService.delete);
        orderItemService.fill(orders);
        model.addAttribute("orders", orders);
        return "bought";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(Integer id) {
        Order order = orderService.get(id);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "redirect: bought";
    }

    @RequestMapping("/buyone")
    public String buyone(Integer product_id, Integer number, @SessionAttribute("user") User user) {
        Product product = productService.get(product_id);
        int orderItemId = 0;
        boolean found = false;
//        List<OrderItem> orderItems = orderItemService.listByUserId(user.getId());
//
//        for (OrderItem orderItem : orderItems) {
//            if(orderItem.getProduct_id() == product_id) {
//                orderItem.setNumber(orderItem.getNumber() + number);
//                orderItemService.update(orderItem);
//                orderItemId = orderItem.getId();
//                found = true;
//                break;
//            }
//        }
        if(!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setNumber(number);
            orderItem.setProduct_id(product_id);
            orderItem.setUser_id(user.getId());
            orderItemService.add(orderItem);
            orderItemId = orderItem.getId();
        }
        return "redirect:buy?orderItemId=" + orderItemId;
    }

    @RequestMapping("/buy")
    public String buy(Model model, @RequestParam("orderItemId") String[] orderItemId, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String s : orderItemId) {
            int id = Integer.parseInt(s);
            OrderItem orderItem = orderItemService.getById(id);
            total += orderItem.getProduct().getPrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        session.setAttribute("orderItems", orderItems);
        model.addAttribute("total", total);
        return"buyPage";
    }

    @RequestMapping("/createOrder")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String createOrder(Model model, Order order, HttpSession session) {
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("orderItems");
        User user = (User) session.getAttribute("user");
        boolean hasStock = true;
        int navigateId = 0;
        synchronized (this) {
            for(OrderItem item : orderItems) {
                Product p = productService.get(item.getProduct_id());
                if(p.getStock() >= item.getNumber()) {
                    p.setStock(p.getStock() - item.getNumber());
                    productService.update(p);
                }else {
                    navigateId = p.getId();
                    hasStock = false;
                    break;
                }
            }
        }
        if(hasStock == false) {
            model.addAttribute("msg" , "没有足够的库存");
            return "redirect: showProduct?product_id=" + navigateId;
        }else{
            String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            order.setOrder_code(orderCode);
            order.setCreate_date(new Date());
            order.setUser_id(user.getId());
            order.setUser(user);
            order.setStatus(OrderService.waitPay);
            float total = orderService.add(order, orderItems);
            return "redirect:payPage?order_id=" + order.getId() +"&total=" + total;
        }
    }

    @RequestMapping("/payed")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public String payed(Integer order_id, float total, Model model) {
        Order order = orderService.get(order_id);
        order.setStatus(OrderService.waitDelivery);
        order.setPay_date(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "payed";
    }

}
