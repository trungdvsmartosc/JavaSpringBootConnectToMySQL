package smartosc.fresher.connectmysql.service;

import smartosc.fresher.connectmysql.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void saveTransactions(List<Transaction> transactions);

    List<Transaction> getTransactionsByAccountId(long accountId);

    Optional<Transaction> getTransactionById(long id);
}
