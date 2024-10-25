package smartosc.fresher.connectmysql.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.kafka.KafkaService;
import smartosc.fresher.connectmysql.model.Transaction;
import smartosc.fresher.connectmysql.repository.TransactionRepository;
import smartosc.fresher.connectmysql.service.HeaderService;
import smartosc.fresher.connectmysql.service.TransactionService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final HeaderService headerService;
    private final KafkaService kafkaProducerService;
    private final String transactionTopicName;
    private final ObjectMapper objectMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  HeaderService headerService,
                                  KafkaService kafkaProducerService,
                                  @Value("${kafka.topics.transaction.name}") String transactionTopicName, ObjectMapper objectMapper) {
        this.transactionRepository = transactionRepository;
        this.headerService = headerService;
        this.kafkaProducerService = kafkaProducerService;
        this.transactionTopicName = transactionTopicName;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void saveTransactions(List<Transaction> transactions, HttpServletRequest request) {
        final var account = headerService.getAccountFromHeader(request);
        final var currentTime = Instant.now();
        transactions.forEach(transaction -> {
            transaction.setAccount(account);
            transaction.setTransactionDate(currentTime);
        });

        try {
            final String message = objectMapper.writeValueAsString(transactions);
            kafkaProducerService.send(transactionTopicName, message);
        } catch (JsonProcessingException e) {
            log.debug("Can't send message caused by: {}", e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(long accountId) {
        return transactionRepository.getTransactionsByAccountId(accountId);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(HttpServletRequest request) {
        final var account = headerService.getAccountFromHeader(request);
        return getTransactionsByAccountId(account.getId());
    }

    @Override
    public Optional<Transaction> getTransactionById(long id) {
        return transactionRepository.findById(id);
    }
}
