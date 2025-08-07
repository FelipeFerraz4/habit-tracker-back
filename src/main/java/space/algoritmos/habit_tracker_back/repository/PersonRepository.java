package space.algoritmos.habit_tracker_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.algoritmos.habit_tracker_back.model.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
