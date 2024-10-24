package smartosc.fresher.connectmysql.security.service;

import jakarta.servlet.http.HttpServletRequest;
import smartosc.fresher.connectmysql.security.dto.*;

import java.io.IOException;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest authenticationRequest);

    AuthenticationResponse refreshToken(HttpServletRequest request) throws IOException;
}
