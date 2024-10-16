package br.com.fiap.app.controller;

import br.com.fiap.app.mapper.UserMapper;
import br.com.fiap.app.request.UserPostRequest;
import br.com.fiap.app.response.UserGetResponse;
import br.com.fiap.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/list")
    public ResponseEntity<List<UserGetResponse>> list() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserGetResponse> create(@RequestBody UserPostRequest request) {
        var userSaved = userService.save(request);
        return ResponseEntity.status(201).body(userMapper.userToGetResponse(userSaved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
