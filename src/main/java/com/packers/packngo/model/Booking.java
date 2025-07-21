package com.packers.packngo.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate bookingDate;
    private Time bookingTime;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id",nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;

    public void setDetails(Booking newBookingDetails) {
        this.setStartDate(newBookingDetails.getStartDate());
        this.setEndDate(newBookingDetails.getEndDate());
        this.setBookingDate(newBookingDetails.getBookingDate());
        this.setBookingTime(newBookingDetails.getBookingTime());
        this.setUsers(newBookingDetails.getUsers());
        this.setTransaction(newBookingDetails.getTransaction());
        this.setVehicle(newBookingDetails.getVehicle());
    }

}
