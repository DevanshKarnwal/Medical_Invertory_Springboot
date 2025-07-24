package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.model.Users;
import com.devansh.Medical.Invertory.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public void addUser(@RequestBody() Users user){
        userService.addUser(user);
    }


}
