package com.devansh.Medical.Invertory.Management.mapper;

import com.devansh.Medical.Invertory.Management.DTO.OrderDTO;
import com.devansh.Medical.Invertory.Management.DTO.ProductDTO;
import com.devansh.Medical.Invertory.Management.DTO.UserDTO;
import com.devansh.Medical.Invertory.Management.models.Orders;
import com.devansh.Medical.Invertory.Management.models.Product;
import com.devansh.Medical.Invertory.Management.models.Users;
import org.springframework.stereotype.Component;

// OrderMapper.java

@Component
public class OrderMapper {
    public OrderDTO toDto(Orders order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setApproved(order.isApproved());
        dto.setPrice(order.getPrice());
        dto.setQuantity(order.getQuantity());
        dto.setGenerationDate(order.getGenerationDate().toString());

        Product product = order.getProduct();
        if (product != null) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setProductId(product.getProductId());
            productDTO.setPrice(product.getPrice());
            productDTO.setCategory(product.getCategory());
            dto.setProduct(productDTO);
        }

        Users user = order.getUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            dto.setUser(userDTO);
        }

        return dto;
    }
}
