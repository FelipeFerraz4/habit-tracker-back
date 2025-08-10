package space.algoritmos.habit_tracker_back.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.algoritmos.habit_tracker_back.dto.CreateUserRequestDTO;
import space.algoritmos.habit_tracker_back.dto.UserResponseDTO;
import space.algoritmos.habit_tracker_back.dto.security.AccountCredentialsDTO;
import space.algoritmos.habit_tracker_back.dto.security.TokenDTO;
import space.algoritmos.habit_tracker_back.model.Person;
import space.algoritmos.habit_tracker_back.model.PersonStatus;
import space.algoritmos.habit_tracker_back.model.auth.Permission;
import space.algoritmos.habit_tracker_back.model.auth.User;
import space.algoritmos.habit_tracker_back.repository.PermissionRepository;
import space.algoritmos.habit_tracker_back.repository.PersonRepository;
import space.algoritmos.habit_tracker_back.repository.UserRepository;
import space.algoritmos.habit_tracker_back.security.jwt.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final PermissionRepository permissionRepository;

    public TokenDTO signIn(AccountCredentialsDTO accountCredentialsDTO) {
        log.info("Tentando autenticar usuário: {}", accountCredentialsDTO.username());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            accountCredentialsDTO.username(),
                            accountCredentialsDTO.password()
                    )
            );
            log.info("Autenticação bem-sucedida para usuário: {}", accountCredentialsDTO.username());
        } catch (Exception e) {
            log.error("Falha na autenticação para usuário {}: {}", accountCredentialsDTO.username(), e.getMessage());
            throw e;
        }

        var user = userRepository.findByUsername(accountCredentialsDTO.username());
        if (user == null) {
            log.error("Usuário não encontrado no banco: {}", accountCredentialsDTO.username());
            throw new UsernameNotFoundException("Username " + accountCredentialsDTO.username() + " not found");
        }
        log.info("Usuário encontrado: {} com roles: {}", accountCredentialsDTO.username(), user.getRoles());

        var token = jwtTokenProvider.createAccessToken(
                accountCredentialsDTO.username(),
                user.getRoles()
        );
        log.info("Token gerado com sucesso para usuário: {}", accountCredentialsDTO.username());

        return token;
    }

    public TokenDTO refreshToken(String username, String refreshToken) {
        var user = userRepository.findByUsername(username);
        TokenDTO token;
        if (user != null) {
            token = jwtTokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return token;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO dto, String roleName) {
        if (userRepository.existsByUsername(dto.email())) {
            throw new IllegalArgumentException("E-mail already exists");
        }

        var permission = permissionRepository.findByDescription(roleName);
        if (permission == null) {
            throw new IllegalArgumentException("No valid roles found");
        }

        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);

        var user = User.builder()
                .username(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .permissions(permissions)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        var person = Person.builder()
                .name(dto.name())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .profilePictureUrl(dto.profilePictureUrl())
                .bio(dto.bio())
                .status(PersonStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        var savedUser = userRepository.save(user);
        var savedPerson = personRepository.save(person);

        var accountCredentialsDTO = new AccountCredentialsDTO(dto.email(), dto.password());
        var tokenDTO = signIn(accountCredentialsDTO);

        return new UserResponseDTO(
                savedPerson.getName(),
                savedUser.getUsername(),
                savedPerson.getPhoneNumber(),
                savedPerson.getProfilePictureUrl(),
                savedPerson.getBio(),
                savedPerson.getStatus(),
                savedPerson.getId(),
                savedUser.getRoles(),
                tokenDTO
        );
    }
}
