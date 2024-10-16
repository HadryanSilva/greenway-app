package br.com.fiap.app.service;

import br.com.fiap.app.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class JWTService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final CustomUserDetailService userDetailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public String generateToken(LoginRequest loginRequest) {

       var user = userDetailService.loadUserByUsername(loginRequest.username());

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        var now = Instant.now();
        var expiresAt = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("greenway-api")
                .claim("roles", user.getAuthorities())
                .subject(user.getUsername())
                .expiresAt(now.plusSeconds(expiresAt))
                .issuedAt(now)
                .build();
        log.info("Token generated for user {}, expires in {} seconds", user.getUsername(), expiresAt);
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Map<String, Object> decodeToken(String token) {
        var jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }
}
