package space.algoritmos.habit_tracker_back.dto;

public record CreateUserRequestDTO(
        String name,
        String email,
        String password,
        String phoneNumber,
        String profilePictureUrl,
        String bio
) {
}
