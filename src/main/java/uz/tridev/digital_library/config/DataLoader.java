package uz.tridev.digital_library.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.tridev.digital_library.entity.Role;
import uz.tridev.digital_library.entity.User;
import uz.tridev.digital_library.repo.RoleRepository;
import uz.tridev.digital_library.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

//@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DataLoader(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = roleRepository.findAll();
        System.out.println(roles.size());

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRoles(new ArrayList<>(List.of(roles.get(0))));

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(passwordEncoder.encode("user"));
        user2.setFullName("Ermamatov Sardor");
        user2.setRoles(new ArrayList<>(List.of(roles.get(1))));

        userRepository.save(user);
        userRepository.save(user2);

    }
}
