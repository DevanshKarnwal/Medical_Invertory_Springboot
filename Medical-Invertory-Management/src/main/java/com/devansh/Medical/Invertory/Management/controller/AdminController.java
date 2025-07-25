package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.models.Inventory;
import com.devansh.Medical.Invertory.Management.models.Product;
import com.devansh.Medical.Invertory.Management.models.SalesHistory;
import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.repository.UserStockRepository;
import com.devansh.Medical.Invertory.Management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserStockRepository userStockRepository;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> usersFetched = adminService.getAllUsers();
        if(usersFetched.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        return ResponseEntity.status(HttpStatus.OK).body(usersFetched);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getSpecificUser(@PathVariable("id") int id){
        Optional<Users> usersFetched = adminService.getSpecificUser(id);
        if(usersFetched.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(usersFetched.get());
    }
    @PostMapping("/userApprove/{id}")
    public ResponseEntity approveUser(@PathVariable("id") int id){
        Optional<Users> usersFetched = adminService.approveUser(id);
        if(usersFetched.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        return ResponseEntity.status(HttpStatus.OK).body("Approved");
    }

    @PostMapping("/userBlock/{id}")
    public ResponseEntity blockUser(@PathVariable("id") int id){
        Optional<Users> usersFetched = adminService.blockUser(id);
        if(usersFetched.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        return ResponseEntity.status(HttpStatus.OK).body("Blocked");
    }

    @PostMapping("/product")
    public ResponseEntity addProduct(@RequestBody() Product product){
        return adminService.addProduct(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
       return adminService.getAllProducts();
    }
    @GetMapping("/product/{id}")
    public ResponseEntity getSpecificProduct(@PathVariable("id") int id){
        return adminService.getSpecificProduct(id);
    }

    @PutMapping("/product")
    public ResponseEntity updateProduct(@RequestBody Product product){
            return adminService.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id){
            return adminService.deleteProduct(id);
    }

    @PostMapping("/inventory")
    public ResponseEntity addToInventory(@RequestBody() Inventory inventory){
        return adminService.addToInventory(inventory);
    }

    @GetMapping("/inventories")
    public ResponseEntity<List<Inventory>> getInventory(){
        return adminService.getInventory();
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity getSpecificInventory(@PathVariable("id") int id){
        return adminService.getSpecificInventory(id);
    }
    @PutMapping("/inventory")
    public ResponseEntity updateInventory(@RequestBody Inventory inventory){
        return adminService.updateInventory(inventory);
    }

    @GetMapping("/orders")
    public ResponseEntity getAllOrders(){
        return adminService.getAllOrders();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity rejectOrder(@PathVariable("id") int id){
        return adminService.deleteOrders(id);
    }

    @Transactional
    @PutMapping("/orders/{id}")
    public ResponseEntity approveOrder(@PathVariable("id") int id){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        SalesHistory salesHistory = SalesHistory.builder()
                .userName(userName)
                .price(0)
                .purchased(true)
                .quantity(userStockRepository.findById(id).get().getQuantity())
                .productName(userStockRepository.findById(id).get().getProduct().getName())
                .build();
        return adminService.approveOrder(id);
    }


}
