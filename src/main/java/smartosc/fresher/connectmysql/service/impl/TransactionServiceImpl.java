package smartosc.fresher.connectmysql.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.exception.ForbiddenException;
import smartosc.fresher.connectmysql.model.Transaction;
import smartosc.fresher.connectmysql.repository.TransactionRepository;
import smartosc.fresher.connectmysql.security.utils.SecurityConstants;
import smartosc.fresher.connectmysql.service.HeaderService;
import smartosc.fresher.connectmysql.service.TransactionService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final EntityManager entityManager;
    private final HeaderService headerService;

    @Override
    @Transactional
    public void saveTransactions(List<Transaction> transactions, HttpServletRequest request) {
        final var account = headerService.getAccountFromHeader(request);
        final var currentTime = Instant.now();
        transactions.forEach(transaction -> {
            transaction.setAccount(account);
            transaction.setTransactionDate(currentTime);
            entityManager.persist(transaction);
        });
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(long accountId) {
        return transactionRepository.getTransactionsByAccountId(accountId);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(long accountId, HttpServletRequest request) {
        final var account = headerService.getAccountFromHeader(request);
        if (account.getId() != accountId) {
            throw new ForbiddenException(SecurityConstants.FORBIDDEN_MESSAGE);
        }
        return getTransactionsByAccountId(accountId);
    }

    @Override
    public Optional<Transaction> getTransactionById(long id) {
        return transactionRepository.findById(id);
    }
}
