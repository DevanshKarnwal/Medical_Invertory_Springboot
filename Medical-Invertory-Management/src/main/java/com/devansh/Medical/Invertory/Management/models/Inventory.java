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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Inventory {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int Id;
    private int quantity;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    @JsonBackReference("product-inventory")
    Product product;
}
