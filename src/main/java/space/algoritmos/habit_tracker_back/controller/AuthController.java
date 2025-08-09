package space.algoritmos.habit_tracker_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import space.algoritmos.habit_tracker_back.dto.security.AccountCredentialsDTO;
import space.algoritmos.habit_tracker_back.dto.security.TokenDTO;
import space.algoritmos.habit_tracker_back.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDTO credentials) {
        if (credentialsAreInvalid(credentials)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        TokenDTO token = authService.signIn(credentials);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return ResponseEntity.ok(token);
    }

    private boolean credentialsAreInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                !StringUtils.hasText(credentials.username()) ||
                !StringUtils.hasText(credentials.password());
    }
}
