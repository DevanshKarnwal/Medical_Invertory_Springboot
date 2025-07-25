package com.devansh.Medical.Invertory.Management.repository;

import com.devansh.Medical.Invertory.Management.models.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory,Integer> {
}
