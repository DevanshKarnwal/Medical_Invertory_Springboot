package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.models.Orders;
import com.devansh.Medical.Invertory.Management.models.UserStock;
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
    public void addUser(@RequestBody() Users user) {
        userService.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody() Users user, @PathVariable("id") int id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping()
    public ResponseEntity deleteUser(@RequestParam("id") int id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/stock")
    public ResponseEntity getStock() {
        return userService.getStock();
    }

    @PutMapping("/stock")
    public ResponseEntity updateStock(@RequestBody UserStock userStock) {
        return userService.updateStock(userStock);
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity deleteStock(@PathVariable("id") int id) {
        return userService.deleteUserStock(id);
    }

    @GetMapping("/orders")
    public ResponseEntity getAllOrders() {
        return adminService.getAllOrders();
    }

    @PostMapping("/order")
    public ResponseEntity addorder(@RequestBody Orders order) {
        return userService.addOrder(order);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id") int id){
        return adminService.deleteOrders(id);
    }

    @GetMapping("/salesHistory")
    public ResponseEntity salesHistory(){
        return userService.salesHistory();
    }

}
