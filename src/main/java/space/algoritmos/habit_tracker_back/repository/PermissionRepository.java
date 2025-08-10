package space.algoritmos.habit_tracker_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.algoritmos.habit_tracker_back.model.auth.Permission;

import java.util.List;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Permission findByDescription(String description);
}
