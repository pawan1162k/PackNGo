package com.packers.packngo.service.admin;

import com.packers.packngo.ExceptionHandler.DatabaseSaveException;
import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Booking;
import com.packers.packngo.model.Transaction;
import com.packers.packngo.repository.BookingRepository;
import com.packers.packngo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminTransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Transaction> getAllTransactions() throws ResourceNotFoundException {
        // Retrieve all transactions from the database
        try{
            return transactionRepository.findAll();
        }catch (RuntimeException e){
            throw new ResourceNotFoundException("Transactions not found");
        }
    }

    public Transaction getTransactionById(Long id) throws ResourceNotFoundException {
        // Retrieve a transaction by ID or throw an exception if not found
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
    }

    public List<Transaction> getTransactionsByUserId(String username) throws ResourceNotFoundException {
        // Retrieve bookings by username, then collect transactions from those bookings
        List<Booking> bookings = bookingRepository.findByUsersUsername(username);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user with ID: " + username);
        }
        return bookings.stream()
                .map(Booking::getTransaction)
                .collect(Collectors.toList());
    }

    public Transaction updateTransactionById(String id, Transaction updatedTransaction) throws ResourceNotFoundException {
        // Update the transaction status if it exists
        Optional<Transaction> existingTransaction = transactionRepository.findById(Long.parseLong(id));
        if (existingTransaction.isPresent()) {
            Transaction transaction = existingTransaction.get();
            transaction.setTransactionStatus(updatedTransaction.getTransactionStatus()); // Assuming a "status" field exists
            try{
                return transactionRepository.save(transaction);
            }catch (RuntimeException e){
                throw new DatabaseSaveException("Unable to complete the transaction " + id);
            }
        } else {
            throw new ResourceNotFoundException("Transaction not found with ID: " + id);
        }
    }
}
