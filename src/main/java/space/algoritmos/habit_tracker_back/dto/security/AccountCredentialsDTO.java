package space.algoritmos.habit_tracker_back.dto.security;

import java.io.Serial;
import java.io.Serializable;

public record AccountCredentialsDTO(
        String username,
        String password
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
