package space.algoritmos.habit_tracker_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.algoritmos.habit_tracker_back.model.auth.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
    boolean existsByUsername(String username);
}
