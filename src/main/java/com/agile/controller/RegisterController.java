package com.agile.controller;

import com.agile.pojo.User;
import com.agile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService = null;

    @RequestMapping("/register")
    public String register(Model model, String name, String password, @RequestParam("password-confirm") String passwordConfirm) {
        boolean isExist = userService.isExist(name);
        if(isExist) {
            String msg = "用户名已经被占用，不能使用";
            model.addAttribute("msg", msg);
            model.addAttribute("username", name);
            return "registerPage";
        }
        if(!password.equals(passwordConfirm)) {
            String msg = "两次输入密码不一致，请重新确认密码";
            model.addAttribute("msg", msg);
            model.addAttribute("username",name);
            return "registerPage";
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userService.add(user);
        return "redirect: registerSuccessPage";
    }
}
