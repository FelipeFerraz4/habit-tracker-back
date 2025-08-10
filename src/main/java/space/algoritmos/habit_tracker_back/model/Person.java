package space.algoritmos.habit_tracker_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Person class represents a person with common user attributes.
 */

@Entity
@Table(name = "people")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,  nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String profilePictureUrl;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
