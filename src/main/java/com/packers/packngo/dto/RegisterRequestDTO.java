package com.packers.packngo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterRequestDTO {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @NotNull
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotNull
    @Pattern(regexp = "USER|ADMIN", message = "Role must be either USER or ADMIN")
    private String role;

    private String securityToken;



    @NotNull
    private String DLNO;

}
