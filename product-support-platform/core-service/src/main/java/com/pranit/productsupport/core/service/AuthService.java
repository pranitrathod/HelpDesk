package com.pranit.productsupport.core.service;

import com.pranit.productsupport.contracts.AuthResponse;
import com.pranit.productsupport.contracts.LoginRequest;
import com.pranit.productsupport.contracts.RegisterRequest;
import com.pranit.productsupport.core.domain.AppUser;
import com.pranit.productsupport.core.domain.UserRole;
import com.pranit.productsupport.core.repository.AppUserRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AppUserRepository users;
    private final PasswordEncoder passwords = new BCryptPasswordEncoder(12);
    private final JwtEncoder encoder;
    private final String issuer;
    private final long ttl;

    public AuthService(AppUserRepository users, JwtEncoder encoder,
            @Value("${app.security.jwt-issuer}") String issuer,
            @Value("${app.security.jwt-ttl-minutes}") long ttl) {
        this.users = users;
        this.encoder = encoder;
        this.issuer = issuer;
        this.ttl = ttl;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = normalize(request.email());
        if (users.existsByEmail(email)) {
            throw ApiException.conflict("An account with this email already exists");
        }
        AppUser user = users.save(new AppUser(request.fullName().trim(), email,
                passwords.encode(request.password()), UserRole.CUSTOMER));
        return token(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        AppUser user = users.findByEmail(normalize(request.email()))
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        if (!user.isActive() || !passwords.matches(request.password(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return token(user);
    }

    private AuthResponse token(AppUser user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(ttl, ChronoUnit.MINUTES))
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", List.of("ROLE_" + user.getRole().name()))
                .build();
        String encoded = encoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
        return new AuthResponse(encoded, "Bearer", ttl * 60, user.getId(), user.getEmail(), user.getRole().name());
    }

    private String normalize(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}