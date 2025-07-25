package com.devansh.Medical.Invertory.Management.repository;

import com.devansh.Medical.Invertory.Management.models.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock,Integer> {
}
