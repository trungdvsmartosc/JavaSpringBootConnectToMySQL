package smartosc.fresher.connectmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartosc.fresher.connectmysql.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select c from Account c where c.username = :usernameOrEmail or c.email = :usernameOrEmail")
    Optional<Account> findAccountByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
