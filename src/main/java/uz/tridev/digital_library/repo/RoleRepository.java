package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.Role;
import uz.tridev.digital_library.entity.enums.RoleName;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByRoleName(RoleName roleName);
}