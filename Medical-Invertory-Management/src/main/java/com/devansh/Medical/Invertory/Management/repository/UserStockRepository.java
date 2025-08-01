package com.devansh.Medical.Invertory.Management.repository;

import com.devansh.Medical.Invertory.Management.models.UserStock;
import com.devansh.Medical.Invertory.Management.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock,Integer> {
    List<UserStock> findByUser(Users user);
    List<UserStock> findByUserId(int userId);
}
