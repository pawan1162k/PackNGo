package com.packers.packngo.repository;

import com.packers.packngo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsersUsername(String userId);

    List<Booking> findByUsersId(Long userId);
}
