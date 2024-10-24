package smartosc.fresher.connectmysql.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartosc.fresher.connectmysql.model.Transaction;
import smartosc.fresher.connectmysql.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public void saveAllTransactions(@RequestBody List<Transaction> transactions, HttpServletRequest request) {
        transactionService.saveTransactions(transactions, request);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("id") long accountId, HttpServletRequest request) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId, request));
    }
}
