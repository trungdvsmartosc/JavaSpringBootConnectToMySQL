package smartosc.fresher.connectmysql.service;

import smartosc.fresher.connectmysql.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account saveAccount(Account account);

    void deleteAccount(int id);

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(int id);

    Account updateAccount(Account account, int id);
}
