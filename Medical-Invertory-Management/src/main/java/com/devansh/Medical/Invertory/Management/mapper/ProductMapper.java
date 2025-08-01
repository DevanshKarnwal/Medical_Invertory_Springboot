package com.devansh.Medical.Invertory.Management.mapper;

import com.devansh.Medical.Invertory.Management.DTO.ProductDTO;
import com.devansh.Medical.Invertory.Management.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .build();
    }
}