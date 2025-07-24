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
@Table(name = "stock")
public class Inventory {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    int Id;
    int quantity;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;
}
