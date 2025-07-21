package com.packers.packngo.service.user;

import com.packers.packngo.ExceptionHandler.DatabaseSaveException;
import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Booking;
import com.packers.packngo.model.Transaction;
import com.packers.packngo.model.Users;
import com.packers.packngo.repository.BookingRepository;
import com.packers.packngo.repository.TransactionRepository;
import com.packers.packngo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction createTransaction() throws ResourceNotFoundException {
        // Get the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Associate the transaction with the user (via booking or directly, if applicable)
//        transaction.setUser(user); // Assuming there's a user field in Transaction

        try{
            Transaction transaction = Transaction.builder()
                    .transactionDate(LocalDate.now())
                    .transactionStatus("PENDING")
                    .transactionTime(LocalTime.now())
                    .transactionType("CREDIT CARD")
                    .build();
            return transactionRepository.save(transaction);
        }catch (RuntimeException e){
            throw new DatabaseSaveException("Error completing transaction ");
        }

        // Save the transaction
    }

    public List<Transaction> getTransactionsForUser() throws ResourceNotFoundException {
        // Get the authenticated user's username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Booking> bookings = bookingRepository.findByUsersUsername(username);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user with ID: " + username);
        }
        // Retrieve all transactions for the user
        return bookings.stream()
                .map(Booking::getTransaction)
                .collect(Collectors.toList());
    }

    public Transaction getTransactionById(Long id) throws ResourceNotFoundException {
        // Retrieve a transaction by ID or throw an exception if not found
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
    }
}
