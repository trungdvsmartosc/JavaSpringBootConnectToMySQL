package smartosc.fresher.connectmysql.security.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartosc.fresher.connectmysql.exception.UnauthorizedException;
import smartosc.fresher.connectmysql.model.Account;
import smartosc.fresher.connectmysql.model.AccountRole;
import smartosc.fresher.connectmysql.repository.AccountRepository;
import smartosc.fresher.connectmysql.repository.TokenRepository;
import smartosc.fresher.connectmysql.security.dto.*;
import smartosc.fresher.connectmysql.security.jwt.JwtTokenManager;
import smartosc.fresher.connectmysql.security.mapper.AccountMapper;
import smartosc.fresher.connectmysql.security.utils.SecurityConstants;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenManager jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        final String username = registerRequest.getUsername();
        if (accountRepository.existsByUsername(username)) {
            throw new EntityExistsException("Account with username " + username + " existed");
        }
        registerRequest.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));

        final Account account = AccountMapper.INSTANCE.convertToAccount(registerRequest);
        account.setAccountRole(AccountRole.USER);
        account.setStatus(true);

        accountRepository.save(account);

        log.info("{} registered successfully!", username);

        return new RegisterResponse(username + " registered successfully!");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        final var account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Account does not exist"));
        final var jwtToken = jwtService.generateToken(account);
        final var refreshToken = jwtService.generateRefreshToken(account);

        revokeAllUserTokens(account);

        saveUserToken(account, refreshToken);

        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Account account, String jwtToken) {
        final var token = Token.builder()
                .account(account)
                .token(jwtToken)
                .type(Token.TYPE.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Account user) {
        final var validUserTokens = tokenRepository.findAllByAccountId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            throw new UnauthorizedException("Authorization header is missing or invalid.");
        }
        final String refreshToken = authHeader.substring(7);
        final String username = jwtService.getUsernameFromToken(refreshToken);
        if (username != null) {
            final var user = accountRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Account with username " + username + " does not exist"));
            if (jwtService.validateToken(refreshToken, user.getUsername())) {
                final var accessToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
            }
        }
        throw new BadRequestException("Bad request");
    }
}
