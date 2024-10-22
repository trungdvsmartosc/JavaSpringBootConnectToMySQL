package smartosc.fresher.connectmysql.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.model.Account;
import smartosc.fresher.connectmysql.repository.AccountRepository;
import smartosc.fresher.connectmysql.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {
        final Optional<Account> oldAccount = accountRepository.findById(account.getId());
        if(oldAccount.isPresent()) {
           throw new EntityExistsException("Account with id " + account.getId() +" existed");
        }
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account updateAccount(Account account, long id) {
        final Optional<Account> oldAccount = accountRepository.findById(id);
        if(oldAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with id " + id +" does not exist");
        }

        oldAccount.get().setUsername(account.getUsername());
        oldAccount.get().setPassword(account.getPassword());
        oldAccount.get().setEmail(account.getEmail());
        oldAccount.get().setAccountRole(account.getAccountRole());
        return accountRepository.save(oldAccount.get());
    }

    @Override
    public Optional<Account> findAccountByUsernameOrEmail(String usernameOrEmail) {
        return accountRepository.findAccountByUsernameOrEmail(usernameOrEmail);
    }
}
