package smartosc.fresher.connectmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartosc.fresher.connectmysql.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
