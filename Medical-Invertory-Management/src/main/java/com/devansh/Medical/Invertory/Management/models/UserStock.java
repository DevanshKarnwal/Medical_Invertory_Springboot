package com.devansh.Medical.Invertory.Management.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Table(name = "userStock")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserStock {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int Id;
    private int quantity;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
