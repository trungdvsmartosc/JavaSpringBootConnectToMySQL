package smartosc.fresher.connectmysql.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartosc.fresher.connectmysql.model.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TYPE type;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;

    @JsonIgnoreType
    public enum TYPE {
        BEARER
    }
}
