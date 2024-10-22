package smartosc.fresher.connectmysql.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.model.Transaction;
import smartosc.fresher.connectmysql.repository.TransactionRepository;
import smartosc.fresher.connectmysql.service.TransactionService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void saveTransactions(List<Transaction> transactions) {
        final var currentTime = Instant.now();
        transactions.forEach(transaction -> {
            transaction.setTransactionDate(currentTime);
            entityManager.persist(transaction);
        });
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(long accountId) {
        return transactionRepository.getTransactionsByAccountId(accountId);
    }

    @Override
    public Optional<Transaction> getTransactionById(long id) {
        return transactionRepository.findById(id);
    }
}
