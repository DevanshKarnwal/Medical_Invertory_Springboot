package com.devansh.Medical.Invertory.Management.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// OrderDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private int id;
    private String generationDate;
    private int quantity;
    private double price;
    private boolean approved;
    private ProductDTO product;
    private UserDTO user;
}
