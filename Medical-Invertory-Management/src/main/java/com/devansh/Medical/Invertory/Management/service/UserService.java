package com.devansh.Medical.Invertory.Management.service;

import com.devansh.Medical.Invertory.Management.models.*;
import com.devansh.Medical.Invertory.Management.repository.OrderRepository;
import com.devansh.Medical.Invertory.Management.repository.SalesHistoryRepository;
import com.devansh.Medical.Invertory.Management.repository.UserRepository;
import com.devansh.Medical.Invertory.Management.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  AuthenticationManager authenticationManager;



    public ResponseEntity createUser(Users user) {
        Users fetched = userRepository.findByName(user.getName());
        if(fetched != null)
            return ResponseEntity.status(HttpStatus.FOUND).body("User name already exists");
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Arrays.asList(Roles.USER));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User Created");
        }
    }
    public ResponseEntity loginUser(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getName(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
        }
    }

    public ResponseEntity updateUser(Users newUser){
        try {
            Optional<Users> current = userRepository.findById(newUser.getId());
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
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            SalesHistory salesHistory = SalesHistory.builder()
                    .userName(userName)
                    .price(0)
                    .sold(true)
                    .quantity(userStockOld.getQuantity()-userStock.getQuantity())
                    .productName(userStock.getProduct().getName())
                    .build();
            userStockRepository.save(userStock);
            return ResponseEntity.ok("User stock updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }

    }

    @Transactional
    public ResponseEntity deleteUserStock(int id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        SalesHistory salesHistory = SalesHistory.builder()
                .userName(userName)
                .price(0)
                .deleted(true)
                .quantity(userStockRepository.findById(id).get().getQuantity())
                .productName(userStockRepository.findById(id).get().getProduct().getName())
                .build();
        userStockRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Stock deleted");
    }

    @Transactional
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
