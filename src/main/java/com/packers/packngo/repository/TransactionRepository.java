package com.packers.packngo.repository;

import com.packers.packngo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Optional<Transaction> findByBookingUsers_Username(String username);
    Optional<Transaction> findByBookingUsersUsername(String username);
}
