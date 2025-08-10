package space.algoritmos.habit_tracker_back.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.algoritmos.habit_tracker_back.dto.security.AccountCredentialsDTO;
import space.algoritmos.habit_tracker_back.dto.security.TokenDTO;
import space.algoritmos.habit_tracker_back.repository.UserRepository;
import space.algoritmos.habit_tracker_back.security.jwt.JwtTokenProvider;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

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
}
