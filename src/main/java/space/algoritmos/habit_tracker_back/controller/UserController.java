package space.algoritmos.habit_tracker_back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.algoritmos.habit_tracker_back.dto.CreateUserRequestDTO;
import space.algoritmos.habit_tracker_back.dto.UserResponseDTO;
import space.algoritmos.habit_tracker_back.service.AuthService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @Value("${spring.security.admin.creation-code}")
    private String adminCreationCode;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody CreateUserRequestDTO dto) {
        var response = authService.createUser(dto, "COMMOM_USER");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserResponseDTO> registerAdmin(
            @RequestBody CreateUserRequestDTO dto,
            @RequestParam("code") String adminCode) {
        if (!adminCode.equals(adminCreationCode)) {
            throw new IllegalArgumentException("Invalid admin creation code");
        }
        var response = authService.createUser(dto, "ADMIN");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

