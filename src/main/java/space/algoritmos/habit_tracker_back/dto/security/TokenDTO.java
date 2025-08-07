package space.algoritmos.habit_tracker_back.dto.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public record TokenDTO(
        String username,
        Boolean authenticated,
        Date created,
        Date expiration,
        String accessToken,
        String refreshToken
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
