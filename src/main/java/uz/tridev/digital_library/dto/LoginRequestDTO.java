package uz.tridev.digital_library.dto;

import lombok.Value;


@Value
public class LoginRequestDTO {
    String username;
    String password;
}
