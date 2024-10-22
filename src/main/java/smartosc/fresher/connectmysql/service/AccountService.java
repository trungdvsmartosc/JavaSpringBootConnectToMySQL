package smartosc.fresher.connectmysql.service;

import smartosc.fresher.connectmysql.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account saveAccount(Account account);

    void deleteAccount(long id);

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(long id);

    Account updateAccount(Account account, long id);

    Optional<Account> findAccountByUsernameOrEmail(String usernameOrEmail);
}