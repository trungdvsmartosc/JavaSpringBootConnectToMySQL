package smartosc.fresher.connectmysql.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartosc.fresher.connectmysql.model.Account;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenManager {

    private final JwtProperties jwtProperties;

    public String generateRefreshToken(Account account) {
        return buildJwtToken(account, jwtProperties.getRefreshExpirationMs());
    }

    public String generateToken(Account account) {
        return buildJwtToken(account, jwtProperties.getTokenExpirationMs());
    }

    private String buildJwtToken(Account account, long expiration) {
        final String username = account.getUsername();
        return JWT.create()
                .withSubject(username)
                .withClaim("role", List.of(Account.ROLE.USER))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    public String getUsernameFromToken(String token) {
        final DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public boolean validateToken(String token, String authenticatedUsername) {
        final String usernameFromToken = getUsernameFromToken(token);
        final boolean equalsUsername = usernameFromToken.equals(authenticatedUsername);
        final boolean tokenExpired = isTokenExpired(token);
        return equalsUsername && !tokenExpired;
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDateFromToken = getExpirationDateFromToken(token);
        return expirationDateFromToken.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        final DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getExpiresAt();
    }

    private DecodedJWT getDecodedJWT(String token) {
        final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes())).build();
        return jwtVerifier.verify(token);
    }
}
