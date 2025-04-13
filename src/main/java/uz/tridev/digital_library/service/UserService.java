package uz.tridev.digital_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.tridev.digital_library.dto.UserDTO;
import uz.tridev.digital_library.entity.Role;
import uz.tridev.digital_library.entity.User;
import uz.tridev.digital_library.entity.enums.RoleName;
import uz.tridev.digital_library.repo.RoleRepository;
import uz.tridev.digital_library.repo.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User registerUser(UserDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepository.existsByFullName(dto.getFullName())) {
            throw new IllegalArgumentException("Full name already taken");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setRoles(roleRepository.findByRoleName(RoleName.ROLE_USER));

        return userRepository.save(user);
    }
}
