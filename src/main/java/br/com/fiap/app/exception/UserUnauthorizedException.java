package br.com.fiap.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserUnauthorizedException extends ResponseStatusException {

    public UserUnauthorizedException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

}
