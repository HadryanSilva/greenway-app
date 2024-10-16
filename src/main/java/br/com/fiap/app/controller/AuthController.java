package br.com.fiap.app.controller;

import br.com.fiap.app.request.LoginRequest;
import br.com.fiap.app.response.LoginResponse;
import br.com.fiap.app.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTService jwtService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var token = jwtService.generateToken(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
