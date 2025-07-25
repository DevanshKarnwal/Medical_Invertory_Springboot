package com.devansh.Medical.Invertory.Management.repository;

import com.devansh.Medical.Invertory.Management.models.Inventory;
import com.devansh.Medical.Invertory.Management.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    Optional<Inventory> findByProduct(Product product);
}
