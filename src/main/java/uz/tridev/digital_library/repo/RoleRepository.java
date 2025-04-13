package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}