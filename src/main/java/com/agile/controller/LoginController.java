package com.agile.controller;

import com.agile.pojo.User;
import com.agile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService = null;

    @RequestMapping("/login")
    public String login(Model model , String name, String password, HttpSession session) {
        User user = userService.get(name, password);
        if(user == null) {
            model.addAttribute("msg", "账号密码错误");
            return "loginPage";
        }
        session.setAttribute("user" , user);
        return "redirect: home";
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.removeAttribute("user");
        return "redirect: home";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "fail";
        }
        return "success";
    }

}
