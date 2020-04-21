package com.cothink.demo.controller;

import com.cothink.demo.model.User;
import com.cothink.demo.service.UserService;
import com.cothink.demo.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/register")
    public JsonResult register(User user){
        return userService.register(user);
    }

}
