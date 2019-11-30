package com.sda.MyShoppingList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class Home {
    @GetMapping("login")
    public @ResponseBody String login(){
        return "login2345";
    }

    @GetMapping("test")
    public String test(){return "test";}
}

