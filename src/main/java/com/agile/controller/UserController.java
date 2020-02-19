package com.agile.controller;

import com.agile.pojo.User;
import com.agile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService = null;

    @RequestMapping("/listUser")
    public String list(Model model) {
        List<User> users = userService.list();
        model.addAttribute("users", users);
        return"admin/listUser";
    }

    @RequestMapping("/editUser")
    public String edit(Model model, Integer id) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        return "admin/editUser";
    }

    @RequestMapping("/updateUser")
    public String update(String password, Integer id) {
        userService.updatePassword(id,password);
        return "redirect: listUser";
    }
}
