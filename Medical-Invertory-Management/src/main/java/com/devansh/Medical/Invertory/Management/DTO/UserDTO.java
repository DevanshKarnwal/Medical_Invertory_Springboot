package com.devansh.Medical.Invertory.Management.DTO;

import com.devansh.Medical.Invertory.Management.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// UserDTO.java\
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private boolean isBlocked;
    private boolean isWaiting;
    private List<Roles> role;
    private LocalDate creationDate;
    private String password;
    private String number;
    private String pincode;
}
