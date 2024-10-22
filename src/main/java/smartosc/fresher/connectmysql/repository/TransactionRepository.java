package smartosc.fresher.connectmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartosc.fresher.connectmysql.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t " +
            "inner join t.account a " +
            "where a.id = :id")
    List<Transaction> getTransactionsByAccountId(@Param("id") long accountId);
}
