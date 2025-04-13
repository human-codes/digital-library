package uz.tridev.digital_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.digital_library.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);
  boolean existsByFullName(String fullName);
}