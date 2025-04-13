package uz.tridev.digital_library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.tridev.digital_library.dto.LoginRequestDTO;
import uz.tridev.digital_library.dto.UserDTO;
import uz.tridev.digital_library.entity.User;
import uz.tridev.digital_library.security.JwtService;
import uz.tridev.digital_library.service.UserService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        var authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        return ResponseEntity.ok().body(jwtService.generateToken((User) authenticate.getPrincipal()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

}
