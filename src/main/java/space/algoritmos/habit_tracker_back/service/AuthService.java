package space.algoritmos.habit_tracker_back.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.algoritmos.habit_tracker_back.dto.security.AccountCredentialsDTO;
import space.algoritmos.habit_tracker_back.dto.security.TokenDTO;
import space.algoritmos.habit_tracker_back.repository.UserRepository;
import space.algoritmos.habit_tracker_back.security.jwt.JwtTokenProvider;

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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        accountCredentialsDTO.username(),
                        accountCredentialsDTO.password()
                )
        );

        var user = userRepository.findByUsername(accountCredentialsDTO.username());
        if (user == null) {
            throw new UsernameNotFoundException("Username " + accountCredentialsDTO.username() + " not found");
        }

        var token = jwtTokenProvider.createAccessToken(
                accountCredentialsDTO.username(),
                user.getRoles()
        );

        return token;
    }
}
