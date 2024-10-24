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
import smartosc.fresher.connectmysql.model.AccountRole;
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
                                .requestMatchers(HttpMethod.GET, pattern).hasAuthority(AccountRole.USER.name())
                                .requestMatchers(HttpMethod.POST, pattern).hasAuthority(AccountRole.USER.name())
                                .requestMatchers(HttpMethod.GET, "/transactions/**").hasAuthority(AccountRole.USER.name())
//                                .requestMatchers(HttpMethod.POST, "/transactions/**").hasAuthority(AccountRole.USER.name())
                                .requestMatchers(HttpMethod.PUT, pattern).hasAnyAuthority(AccountRole.ADMIN.name(), AccountRole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, pattern).hasAuthority(AccountRole.SUPER_ADMIN.name())
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
