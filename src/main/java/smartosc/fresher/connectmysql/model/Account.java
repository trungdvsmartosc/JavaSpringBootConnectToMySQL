package smartosc.fresher.connectmysql.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;

    private boolean status;

    private String role;

    public static class ROLE {
        private ROLE() {
        }
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String SUPER_ADMIN = "SUPER_ADMIN";
    }

    @Transient
    private List<Transaction> transactions;
}
