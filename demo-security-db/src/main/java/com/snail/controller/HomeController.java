package com.snail.controller;

import com.snail.model.User;
import com.snail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author snail  2018年1月3日
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;


    @GetMapping({"/"})
    public String root() {
        return "index";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/register")
    public String doRegister(User userEntity) {
        // 此处省略校验逻辑
        if (userService.save (userEntity) != null)
            return "redirect:register?success";
        return "redirect:register?error";
    }
    @GetMapping("/register")
    public String doRegister() {
        return "register";
    }
}
