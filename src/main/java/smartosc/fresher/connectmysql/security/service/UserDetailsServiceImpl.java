package smartosc.fresher.connectmysql.security.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.model.Account;
import smartosc.fresher.connectmysql.model.AccountRole;
import smartosc.fresher.connectmysql.repository.AccountRepository;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Account with username " + username + " does not exist"));

        final String authenticatedUsername = account.getUsername();
        final String authenticatedPassword = account.getPassword();
        final AccountRole accountRole = account.getAccountRole();
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(accountRole.name());

        return new User(
                authenticatedUsername,
                authenticatedPassword,
                Collections.singletonList(simpleGrantedAuthority)
        );
    }
}
