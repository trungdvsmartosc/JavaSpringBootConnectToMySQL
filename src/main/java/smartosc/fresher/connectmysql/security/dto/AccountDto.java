package smartosc.fresher.connectmysql.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smartosc.fresher.connectmysql.model.AccountRole;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {

    private String username;

    private String password;

    private AccountRole accountRole;
}
