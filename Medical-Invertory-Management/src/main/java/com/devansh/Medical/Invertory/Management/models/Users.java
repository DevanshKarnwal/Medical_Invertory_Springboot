package com.devansh.Medical.Invertory.Management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Users {
    @GeneratedValue(strategy = SEQUENCE)
    @Id
    private int id;
    @NonNull
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    @NonNull
    private String password;
    @CurrentTimestamp
    private LocalDate creationDate;
    private boolean isBlocked = false;
    private boolean isWaiting = true;
    private String number;
    private String pincode;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
//    @JsonManagedReference("order-user")
    private List<Orders> orders = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
            @Enumerated(EnumType.STRING)
    private List<Roles> role = new ArrayList<>();
}
