package com.packers.packngo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleID;

    @NotNull
    @Column(unique = true, nullable = false)
    private String pickUpLocation;

    @NotNull
    @Column(unique = true, nullable = false)
    private String capacity;

    @NotNull
    @Column(unique = true, nullable = false)
    private String type;

    @NotNull
    @Column(unique = true, nullable = false)
    private String availability;

    @NotNull
    @Column(unique = true, nullable = false)
    private String licensePlate;

    @NotNull
    @Column(unique = true, nullable = false)
    private String rentalPrice;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore // Prevents infinite recursion during serialization
    private List<Booking> bookings;

}
