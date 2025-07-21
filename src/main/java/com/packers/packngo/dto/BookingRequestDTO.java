package com.packers.packngo.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate bookingDate;
    private Time bookingTime;
    private String status;
    private String licensePlate;
    private Long transactionID;

}
