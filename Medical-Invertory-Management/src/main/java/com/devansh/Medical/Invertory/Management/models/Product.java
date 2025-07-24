package com.devansh.Medical.Invertory.Management.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    int Id;
    @Column(unique = true,nullable = false)
    String productId;
    String name;
    double price;
    String category;

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    Inventory inventory;

}
