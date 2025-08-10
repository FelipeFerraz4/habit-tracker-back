package space.algoritmos.habit_tracker_back.dto;

import space.algoritmos.habit_tracker_back.dto.security.TokenDTO;
import space.algoritmos.habit_tracker_back.model.PersonStatus;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
        String name,
        String email,
        String phoneNumber,
        String profilePictureUrl,
        String bio,
        PersonStatus personStatus,
        UUID personId,
        List<String> roles,
        TokenDTO token
) {
}
