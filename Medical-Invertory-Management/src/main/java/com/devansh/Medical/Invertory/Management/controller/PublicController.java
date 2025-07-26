package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.models.LoginRequest;
import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.repository.UserRepository;
import com.devansh.Medical.Invertory.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("public")
@RestController
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody() Users user){

        return userService.createUser(user);
    }
    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody() LoginRequest loginRequest){

        return userService.loginUser(loginRequest);
    }
}
