package com.devansh.Medical.Invertory.Management.service;

import com.devansh.Medical.Invertory.Management.models.Inventory;
import com.devansh.Medical.Invertory.Management.models.Product;
import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.repository.InventoryRepository;
import com.devansh.Medical.Invertory.Management.repository.ProductRepository;
import com.devansh.Medical.Invertory.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Users> getAllUsers() {
        return null;
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
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        return ResponseEntity.status(HttpStatus.OK).body(fetchedProduct);
    }




    public ResponseEntity addToInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
        return ResponseEntity.status(HttpStatus.OK).body("Product created");
    }

    public ResponseEntity getInventory() {
        List<Inventory> fetchedInventory = inventoryRepository.findAll();
        if(fetchedInventory.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        return ResponseEntity.status(HttpStatus.OK).body(fetchedInventory);
    }
}
