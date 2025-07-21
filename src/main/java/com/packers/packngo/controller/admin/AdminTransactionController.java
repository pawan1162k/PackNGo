package com.packers.packngo.controller.admin;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Transaction;
import com.packers.packngo.repository.TransactionRepository;
import com.packers.packngo.service.admin.AdminTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/transaction")
public class AdminTransactionController {

    @Autowired
    private AdminTransactionService adminTransactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() throws ResourceNotFoundException {
        // Get all transactions logic (dummy implementation)
        return ResponseEntity.ok().body(adminTransactionService.getAllTransactions());
    }

    //Get transaction by transactionID
    @GetMapping("/{id}")
    public Object getTransactionById(@PathVariable Long id) throws ResourceNotFoundException {
        // Get a specific transaction by ID logic (dummy implementation)
        return ResponseEntity.ok().body(adminTransactionService.getTransactionById(id));
    }

    //Get transaction by Username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable String username) throws ResourceNotFoundException {
        // Get all transactions of a specific user logic (dummy implementation)
        return ResponseEntity.ok().body(adminTransactionService.getTransactionsByUserId(username));
    }

    @PutMapping("/{id}")
    public String updateTransactionById(@PathVariable String id, @RequestBody Transaction transaction) throws ResourceNotFoundException {
        // Update transaction status by ID logic (dummy implementation)
        adminTransactionService.updateTransactionById(id,transaction);
        return "Transaction updated successfully";
    }
}
