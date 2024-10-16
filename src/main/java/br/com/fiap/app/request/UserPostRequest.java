package br.com.fiap.app.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserPostRequest {

    private String username;
    private String password;
    private String email;
    private String role;

}
