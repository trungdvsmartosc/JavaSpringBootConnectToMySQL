package smartosc.fresher.connectmysql.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import smartosc.fresher.connectmysql.model.Account;
import smartosc.fresher.connectmysql.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/accounts/register", "/accounts/login", "/accounts/refresh-token",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final String pattern = "/accounts/**";
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, pattern).hasAuthority(Account.ROLE.USER)
                                .requestMatchers(HttpMethod.POST, pattern).hasAuthority(Account.ROLE.USER)
                                .requestMatchers(HttpMethod.GET, "/transactions/**").hasAuthority(Account.ROLE.USER)
//                                .requestMatchers(HttpMethod.POST, "/transactions/**").hasAuthority(Account.ROLE.USER)
                                .requestMatchers(HttpMethod.PUT, pattern).hasAnyAuthority(Account.ROLE.ADMIN, Account.ROLE.SUPER_ADMIN)
                                .requestMatchers(HttpMethod.DELETE, pattern).hasAuthority(Account.ROLE.SUPER_ADMIN)
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
