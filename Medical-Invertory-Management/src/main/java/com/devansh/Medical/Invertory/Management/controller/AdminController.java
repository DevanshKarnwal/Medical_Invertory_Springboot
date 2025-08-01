package com.devansh.Medical.Invertory.Management.controller;

import com.devansh.Medical.Invertory.Management.DTO.ProductDTO;
import com.devansh.Medical.Invertory.Management.DTO.UserDTO;
import com.devansh.Medical.Invertory.Management.models.*;
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

    @PostMapping("/createUser")
    public ResponseEntity createAdminUser(@RequestBody() Users user){

        return adminService.createAdminUser(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersFetched = adminService.getAllUsers();
        if (usersFetched.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersFetched);
    }

    @GetMapping("/user")
    public ResponseEntity getSpecificUser(@RequestParam int id){
        Optional<Users> usersFetched = adminService.getSpecificUser(id);
        if(usersFetched.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(usersFetched.get());
    }

    @GetMapping("/userName")
    public ResponseEntity getSpecificUserByName(@RequestParam String name){
        return adminService.getSpecificUserByName(name);
    }

    @PostMapping("/userApprove")
    public ResponseEntity approveUser(@RequestParam int id){
        Optional<Users> usersFetched = adminService.approveUser(id);
        if(usersFetched.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        return ResponseEntity.status(HttpStatus.OK).body("Approved");
    }

    @PostMapping("/userBlock")
    public ResponseEntity blockUser(@RequestParam int id){
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
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
       return adminService.getAllProducts();
    }
    @GetMapping("/product")
    public ResponseEntity getSpecificProduct(@RequestParam int id){
        return adminService.getSpecificProduct(id);
    }

    @PutMapping("/product")
    public ResponseEntity updateProduct(@RequestBody Product product){
            return adminService.updateProduct(product);
    }

    @DeleteMapping("/product")
    public ResponseEntity deleteProduct(@RequestParam int id){
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

    @GetMapping("/inventory")
    public ResponseEntity getSpecificInventory(@RequestParam int id){
        return adminService.getSpecificInventory(id);
    }
    @GetMapping("/inventoryProduct")
    public ResponseEntity getSpecificInventoryByProduct(@RequestParam int id){
        return adminService.getSpecificInventoryByProduct(id);
    }
    @PutMapping("/inventory")
    public ResponseEntity updateInventory(@RequestBody Inventory inventory){
        return adminService.updateInventory(inventory);
    }

    @GetMapping("/orders")
    public ResponseEntity getAllOrders(){
        return adminService.getAllOrders();
    }

    @DeleteMapping("/orders")
    public ResponseEntity rejectOrder(@RequestParam int id){
        return adminService.deleteOrders(id);
    }

    @PutMapping("/orders")
    public ResponseEntity<?> approveOrder(@RequestParam int id) {
        ResponseEntity<?> response = adminService.approveOrder(id);

        if (response.getStatusCode().is2xxSuccessful()) {
            Orders approvedOrder = (Orders) response.getBody();

            // Create UserStock entry after successful approval
            UserStock userStock = UserStock.builder()
                    .product(approvedOrder.getProduct())
                    .quantity(approvedOrder.getQuantity())
                    .user(approvedOrder.getUser())
                    .build();

            userStockRepository.save(userStock);
        }

        return response;
    }



}
