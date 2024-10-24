package smartosc.fresher.connectmysql.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import smartosc.fresher.connectmysql.model.Account;
import smartosc.fresher.connectmysql.repository.AccountRepository;
import smartosc.fresher.connectmysql.security.jwt.JwtTokenManager;

@Service
@RequiredArgsConstructor
public class HeaderService {

    private final JwtTokenManager jwtService;
    private final AccountRepository accountRepository;

    public Account getAccountFromHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken = authHeader.substring(7);
        final String username = jwtService.getUsernameFromToken(refreshToken);
        return accountRepository.findByUsername(username).orElseThrow();
    }
}
