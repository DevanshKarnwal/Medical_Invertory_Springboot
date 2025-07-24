package com.devansh.Medical.Invertory.Management.service;

import com.devansh.Medical.Invertory.Management.model.Users;
import com.devansh.Medical.Invertory.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(Users user){
        userRepository.save(user);
    }

}
