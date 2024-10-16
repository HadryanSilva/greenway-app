package br.com.fiap.app.service;

import br.com.fiap.app.exception.NotFoundException;
import br.com.fiap.app.exception.UserUnauthorizedException;
import br.com.fiap.app.mapper.UserMapper;
import br.com.fiap.app.model.User;
import br.com.fiap.app.repository.UserRepository;
import br.com.fiap.app.request.UserPostRequest;
import br.com.fiap.app.response.UserGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public List<UserGetResponse> listAllUsers() {
        if(!isAdmin()) {
            throw new UserUnauthorizedException("You are not allowed to access this resource");
        }
        var users = userRepository.findAll();
        return userMapper.usersToGetResponseList(users);
    }

    public User save(UserPostRequest request) {
        var user = userMapper.postToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("Saving user: {}", user.getUsername());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if(!isAdmin()) {
            throw new UserUnauthorizedException("You are not allowed to access this resource");
        }
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(user.getId());
        log.info("User deleted successfully");
    }

    private Boolean isAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var details = ((JwtAuthenticationToken) auth).getToken();
        var token = details.getTokenValue();
        var claims = jwtService.decodeToken(token);
        return claims.get("roles").toString().contains("ADMIN");
    }

}
