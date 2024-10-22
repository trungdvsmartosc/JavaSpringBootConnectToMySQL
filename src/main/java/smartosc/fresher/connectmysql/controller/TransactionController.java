package smartosc.fresher.connectmysql.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void saveAllTransactions(@RequestBody List<Transaction> transactions) {
        transactionService.saveTransactions(transactions);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<Transaction>> saveAllTransactions(@PathVariable("id") long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
    }
}
