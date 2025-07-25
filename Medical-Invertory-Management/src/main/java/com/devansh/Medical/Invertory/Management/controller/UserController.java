package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.service.AdminService;
import com.devansh.Medical.Invertory.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @PostMapping()
    public void addUser(@RequestBody() Users user){
        userService.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody() Users user, @PathVariable("id") int id){
            return userService.updateUser(user,id);
    }

    @DeleteMapping()
    public ResponseEntity deleteUser(@RequestParam("id") int id){
        return userService.deleteUser(id);
    }
    @GetMapping("/orders")
    public ResponseEntity getAllOrders(){
        return adminService.getAllOrders();
    }


}
