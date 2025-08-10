package space.algoritmos.habit_tracker_back.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import space.algoritmos.habit_tracker_back.dto.security.TokenDTO;
import space.algoritmos.habit_tracker_back.exception.InvalidJwtAutenticationException;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${spring.security.jwt.token.secret:your_jwt_secret_key}")
    private String secretKey;

    @Value("${spring.security.jwt.token.expiration:3600000}") // Default: 1 hour
    private long validityInMilliseconds;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        // Encode secret key to Base64
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public TokenDTO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + validityInMilliseconds);
        Date refreshTokenValidity = new Date(now.getTime() + validityInMilliseconds * 3);

        String accessToken = getAccessToken(username, roles, now, accessTokenValidity);
        String refreshToken = getRefreshToken(username, roles, now, refreshTokenValidity);

        return new TokenDTO(username, true, now, accessTokenValidity, accessToken, refreshToken);
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withClaim("type", "access")
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withIssuer(issuerURL)
                .sign(algorithm);
    }

    private String getRefreshToken(String username, List<String> roles, Date now, Date validity) {
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withClaim("type", "refresh")
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            if (decodedJWT.getExpiresAt().before(new Date())) {
                throw new InvalidJwtAutenticationException("JWT token expired");
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAutenticationException("Expired or Invalid JWT Token!");
        }
    }

    private DecodedJWT decodeToken(String token) {
        String issuerURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuerURL)
                .build();
        return verifier.verify(token);
    }
}
