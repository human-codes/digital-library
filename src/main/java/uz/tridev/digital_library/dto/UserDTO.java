package uz.tridev.digital_library.dto;

import lombok.Value;

@Value
public class UserDTO {
    String username;
    String password;

    String email;
    String phone;
    String fullName;

}
