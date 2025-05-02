package com.se.user.Accountant.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accountant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String department;
    Double experience;
    String username;

    @OneToMany(mappedBy = "accountant", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    List<Qualification> qualifications;

//    @OneToOne
//    @JoinColumn(name = "username")
//    User user;
}
