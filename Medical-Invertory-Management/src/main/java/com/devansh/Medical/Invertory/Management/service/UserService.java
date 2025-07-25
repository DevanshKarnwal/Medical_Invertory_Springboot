package com.devansh.Medical.Invertory.Management.service;

import com.devansh.Medical.Invertory.Management.models.Orders;
import com.devansh.Medical.Invertory.Management.models.SalesHistory;
import com.devansh.Medical.Invertory.Management.models.UserStock;
import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.repository.OrderRepository;
import com.devansh.Medical.Invertory.Management.repository.SalesHistoryRepository;
import com.devansh.Medical.Invertory.Management.repository.UserRepository;
import com.devansh.Medical.Invertory.Management.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SalesHistoryRepository salesHistoryRepository;

    public void addUser(Users user){
        userRepository.save(user);
    }

    public ResponseEntity updateUser(Users newUser, int id){
        try {
            Optional<Users> current = userRepository.findById(id);
            if(current.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            Users user = current.get();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }

    }

    public ResponseEntity deleteUser(int id) {
        Optional<Users> current = userRepository.findById(id);
        if(current.isEmpty())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        Users user = current.get();
        userRepository.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }

    public ResponseEntity getStock() {
        List<UserStock> fetchedStock = userStockRepository.findAll();
        if (fetchedStock.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory is Empty");
        return ResponseEntity.status(HttpStatus.OK).body(fetchedStock);
    }

    public ResponseEntity updateStock(UserStock userStock) {
        try {
            Optional<UserStock> current = userStockRepository.findById(userStock.getId());
            if(current.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            UserStock userStockOld = current.get();
            userStockOld.setQuantity(userStock.getQuantity());

            userStockRepository.save(userStock);
            return ResponseEntity.ok("User stock updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }

    }

    @Transactional
    public ResponseEntity deleteUserStock(int id) {
        userStockRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Stock deleted");
    }

    public ResponseEntity addOrder(Orders order) {
        try {
            orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.OK).body("Order created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }

    public ResponseEntity salesHistory() {
        List<SalesHistory> fetchedSalesHistory = salesHistoryRepository.findAll();
        if (fetchedSalesHistory.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory is Empty");
        return ResponseEntity.status(HttpStatus.OK).body(fetchedSalesHistory);
    }
}
