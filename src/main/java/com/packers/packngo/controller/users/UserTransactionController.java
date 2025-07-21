package com.packers.packngo.controller.users;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.dto.TransactionRequestDTO;
import com.packers.packngo.model.Transaction;
import com.packers.packngo.repository.TransactionRepository;
import com.packers.packngo.service.admin.AdminTransactionService;
import com.packers.packngo.service.user.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/transaction/")
public class UserTransactionController {
    @Autowired
    private UserTransactionService userTransactionService;

    @PostMapping("/")
    public ResponseEntity<Transaction> create() throws ResourceNotFoundException {

        return ResponseEntity.ok().body(userTransactionService.createTransaction());
    }

    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getTransactions() throws ResourceNotFoundException {

        return ResponseEntity.ok().body(userTransactionService.getTransactionsForUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) throws ResourceNotFoundException {

        return ResponseEntity.ok().body(userTransactionService.getTransactionById(id));
    }

}
