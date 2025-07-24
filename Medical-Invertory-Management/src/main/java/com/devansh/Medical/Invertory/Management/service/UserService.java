package com.devansh.Medical.Invertory.Management.service;

import com.devansh.Medical.Invertory.Management.models.Users;
import com.devansh.Medical.Invertory.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(Users user){
        userRepository.save(user);
    }

    public ResponseEntity updateUser(Users newUser, int id){
         Optional<Users> current = userRepository.findById(id);
         if(current.isEmpty())
             return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
         Users user = current.get();
         user.setName(newUser.getName());
         user.setEmail(newUser.getEmail());
         user.setPassword(newUser.getPassword());
         userRepository.save(user);
         return ResponseEntity.ok("User updated successfully");

    }

    public ResponseEntity deleteUser(int id) {
        Optional<Users> current = userRepository.findById(id);
        if(current.isEmpty())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        Users user = current.get();
        userRepository.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }
}
