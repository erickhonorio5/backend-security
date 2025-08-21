package rct.sistema.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rct.sistema.backend.controllers.dto.request.AuthUserRequestDTO;
import rct.sistema.backend.controllers.mapper.AuthUserControllerMapper;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.usecases.AuthUserUseCase.AuthUserUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserUseCase authUserUseCase;
    private final AuthUserControllerMapper authUserControllerMapper;

    @PostMapping
    public ResponseEntity<AuthUser> createUser(@Valid @RequestBody AuthUserRequestDTO requestDTO) {
        var savedUser = authUserUseCase.save(authUserControllerMapper.toAuthUser(requestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthUser> getUserById(@PathVariable Long id) {
        var user = authUserUseCase.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AuthUser> getUserByUsername(@PathVariable String username) {
        var user = authUserUseCase.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthUser> getUserByEmail(@PathVariable String email) {
        var user = authUserUseCase.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public List<AuthUser> getAllUsers() {
        return authUserUseCase.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        authUserUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
