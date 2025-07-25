package com.devansh.Medical.Invertory.Management.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private boolean isApproved = false;
    @CurrentTimestamp
    private LocalDate generationDate;
    @NonNull
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    @JsonBackReference("product-orders")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonBackReference("order-user")
    private Users user;
    private double price;
}
