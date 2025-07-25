package com.devansh.Medical.Invertory.Management.repository;

import com.devansh.Medical.Invertory.Management.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
}
