package com.packers.packngo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        @Email
        private String username;

        @Column(nullable = false)
        private String name;

        @Column(unique = true, nullable = false)
        private String phone;

        @Column(nullable = false)
        private String password;

        @Column(unique = true, nullable = false)
        private String DLNO;

        @Column(nullable = false)
        private String role; // "USER" or "ADMIN"



        @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JsonIgnore // Prevents infinite recursion during serialization
        private List<Booking> bookings;
}
