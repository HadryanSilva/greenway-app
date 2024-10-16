package br.com.fiap.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final Environment environment;

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from Greenway!");
    }

    @GetMapping("/porta")
    public ResponseEntity<String> porta() {
        return ResponseEntity.ok("Porta: " + environment.getProperty("local.server.port"));
    }

}
