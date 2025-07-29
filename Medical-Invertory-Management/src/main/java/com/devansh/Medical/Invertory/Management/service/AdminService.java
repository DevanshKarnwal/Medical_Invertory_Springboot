package com.devansh.Medical.Invertory.Management.service;

import com.devansh.Medical.Invertory.Management.models.*;
import com.devansh.Medical.Invertory.Management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    public ResponseEntity getSpecificUserByName(String name) {
        Users byName = userRepository.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(byName);
    }
    public ResponseEntity createAdminUser(Users user) {
        Users fetched = userRepository.findByName(user.getName());
        if(fetched != null)
            return ResponseEntity.status(HttpStatus.FOUND).body("User name already exists");
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Arrays.asList(Roles.USER,Roles.ADMIN));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User Created");
        }
    }
    @Transactional
    public Optional<Users> approveUser(int id) {
        Optional<Users>user = getSpecificUser(id);
        if (user.isEmpty())
            return user;
        Users current = user.get();
        current.setWaiting(false);
        userRepository.save(current);
        return Optional.of(current);
    }

    public Optional<Users> blockUser(int id) {
        Optional<Users>user = getSpecificUser(id);
        if (user.isEmpty())
            return user;
        Users current = user.get();
        current.setBlocked(true);
        userRepository.save(current);
        return Optional.of(current);
    }

    public Optional<Users> getSpecificUser(int id) {
        return userRepository.findById(id);
    }

    public ResponseEntity addProduct(Product product) {
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product created");
    }
    public ResponseEntity getAllProducts() {
        List<Product> fetchedProduct = productRepository.findAll();
        if(fetchedProduct.isEmpty())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        return ResponseEntity.status(HttpStatus.OK).body(fetchedProduct);
    }

    public ResponseEntity getSpecificProduct(int id) {
        Optional<Product> fetchedProduct = productRepository.findById(id);
        if(fetchedProduct.isEmpty())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        return ResponseEntity.status(HttpStatus.OK).body(fetchedProduct.get());
    }

    @Transactional
    public ResponseEntity updateProduct(Product newProduct) {
        Optional<Product> current = productRepository.findById(newProduct.getId());
        if(current.isEmpty())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        Product product = current.get();
        product.setName(newProduct.getName());
        product.setCategory(newProduct.getCategory());
        product.setPrice(newProduct.getPrice());
        productRepository.save(product);
        return ResponseEntity.ok("Product updated successfully");
    }

    public ResponseEntity deleteProduct(int id){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }

    public ResponseEntity addToInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
        return ResponseEntity.status(HttpStatus.OK).body("Inventory created");
    }

    public ResponseEntity getInventory() {
        List<Inventory> fetchedInventory = inventoryRepository.findAll();
        if(fetchedInventory.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        return ResponseEntity.status(HttpStatus.OK).body(fetchedInventory);
    }


    public ResponseEntity getSpecificInventory(int id) {
        Optional<Inventory> fetchedInventory = inventoryRepository.findById(id);
        if(fetchedInventory.isEmpty())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        return ResponseEntity.status(HttpStatus.OK).body(fetchedInventory.get());
    }

    @Transactional
    public ResponseEntity updateInventory(Inventory newInventory) {
        try {
            Optional<Inventory> current = inventoryRepository.findById(newInventory.getId());

            if(current.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            Optional<Inventory> existing = inventoryRepository.findByProduct(newInventory.getProduct());
            if (existing.isPresent() && existing.get().getId() != newInventory.getId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inventory for this product already exists");
            }
            Inventory inventory = current.get();
            inventory.setQuantity(newInventory.getQuantity());

            inventoryRepository.save(inventory);
            return ResponseEntity.ok("Inventory updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already exists");
        }
    }
    public ResponseEntity<List<Orders>> getAllOrders(){
            List<Orders> fetchOrder = orderRepository.findAll();
            if(fetchOrder.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            fetchOrder = fetchOrder.stream().filter(order -> !order.isApproved()).toList();
            return ResponseEntity.status(HttpStatus.OK).body(fetchOrder);
    }



    public ResponseEntity deleteOrders(int id) {
        orderRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted");
    }

    @Transactional
    public ResponseEntity approveOrder(int id) {
        Optional<Orders> fetchedOrder = orderRepository.findById(id);
        if (fetchedOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        Orders currentOrder = fetchedOrder.get();
        Product product = currentOrder.getProduct();
        int quantity = currentOrder.getQuantity();
        Optional<Inventory> inventoryOptional = inventoryRepository.findByProduct(product);

        if (inventoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Inventory not found for product: " + product.getName());
        }
        Inventory inventory = inventoryOptional.get();

        if (inventory.getQuantity() < quantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough inventory to approve the order");
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        currentOrder.setApproved(true);
        inventoryRepository.save(inventory);
        orderRepository.save(currentOrder);

        UserStock toAdd = UserStock.builder().quantity(quantity).product(product).build();
        userStockRepository.save(toAdd);
        return ResponseEntity.ok("Order approved successfully");

    }



}
