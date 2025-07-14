package space.algoritmos.habit_tracker_back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private String name;
    private String email;
    private String phoneNumber;
    private String profilePictureUrl;
    private String bio;
}
