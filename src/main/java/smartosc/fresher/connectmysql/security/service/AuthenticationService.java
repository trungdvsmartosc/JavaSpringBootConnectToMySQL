package smartosc.fresher.connectmysql.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import smartosc.fresher.connectmysql.security.dto.*;

import java.io.IOException;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest authenticationRequest);

    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
