package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.DTO.OrderDTO;
import com.devansh.Medical.Invertory.Management.DTO.PlaceOrderRequest;
import com.devansh.Medical.Invertory.Management.DTO.ProductDTO;
import com.devansh.Medical.Invertory.Management.DTO.UserStockDTO;
import com.devansh.Medical.Invertory.Management.models.Orders;
import com.devansh.Medical.Invertory.Management.models.Product;
import com.devansh.Medical.Invertory.Management.models.UserStock;
import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.service.AdminService;
import com.devansh.Medical.Invertory.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;



    @GetMapping("/userName")
    public ResponseEntity getSpecificUserByName(@RequestParam String name){
        return adminService.getSpecificUserByName(name);
    }

    @PutMapping()
    public ResponseEntity updateUser(@RequestBody() Users user) {
        return userService.updateUser(user);
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

    @DeleteMapping("/stock")
    public ResponseEntity deleteStock(@RequestParam int id) {
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

    @DeleteMapping("/order")
    public ResponseEntity deleteOrder(@RequestParam int id){
        return adminService.deleteOrders(id);
    }

    @GetMapping("/salesHistory")
    public ResponseEntity salesHistory(){
        return userService.salesHistory();
    }

    @GetMapping("/user-stock")
    public ResponseEntity<List<UserStockDTO>> getUserStock(@RequestParam int userId) {
        List<UserStockDTO> stocks = userService.getStockByUserId(userId);
        return ResponseEntity.ok(stocks);
    }
    @PostMapping("/order/place")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request) {
        try {
            OrderDTO orderDTO = userService.placeOrder(request);
            return ResponseEntity.ok(orderDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/products/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return userService.getAllProducts();

    }
}
