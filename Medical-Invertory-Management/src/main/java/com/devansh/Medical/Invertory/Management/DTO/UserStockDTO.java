package com.devansh.Medical.Invertory.Management.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStockDTO {
    private int userId;
    private int productId;
    private String productName;
    private double productPrice;
    private int quantity;
}