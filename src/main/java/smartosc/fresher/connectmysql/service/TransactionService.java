package smartosc.fresher.connectmysql.service;

import jakarta.servlet.http.HttpServletRequest;
import smartosc.fresher.connectmysql.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void saveTransactions(List<Transaction> transactions, HttpServletRequest request);

    List<Transaction> getTransactionsByAccountId(HttpServletRequest request);

    List<Transaction> getTransactionsByAccountId(long accountId);

    Optional<Transaction> getTransactionById(long id);
}
