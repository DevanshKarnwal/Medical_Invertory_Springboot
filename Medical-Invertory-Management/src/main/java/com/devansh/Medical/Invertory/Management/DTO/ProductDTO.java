package com.devansh.Medical.Invertory.Management.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ProductDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private int id;
    private String productId;
    private String name;
    private double price;
    private String category;
}
