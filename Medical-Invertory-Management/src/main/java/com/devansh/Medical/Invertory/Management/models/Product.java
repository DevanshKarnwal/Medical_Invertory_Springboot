package com.devansh.Medical.Invertory.Management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int Id;
    @Column(unique = true,nullable = false)
    private String productId;
    private String name;
    double price;
    private String category;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
//    @JsonManagedReference("product-orders")
    private List<Orders> orders = new ArrayList<>();
    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference("product-inventory")
    private Inventory inventory;

}
