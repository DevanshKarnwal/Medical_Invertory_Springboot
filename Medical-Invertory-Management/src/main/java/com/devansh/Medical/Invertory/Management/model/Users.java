package com.devansh.Medical.Invertory.Management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.lang.reflect.Type;
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
public class Users {
    @GeneratedValue(strategy = SEQUENCE)
    @Id
    int id;
    @NonNull
    String name;
    @Column(unique = true)
    String email;
    @NonNull
    String password;
    @CurrentTimestamp
    LocalDate creationDate;
    boolean isBlocked = true;
    boolean isWaiting = true;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
            @Enumerated(EnumType.STRING)
    List<Roles> role = new ArrayList<>();
}
