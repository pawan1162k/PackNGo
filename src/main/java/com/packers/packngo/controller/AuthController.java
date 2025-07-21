package com.packers.packngo.controller;

import com.packers.packngo.dto.*;
import com.packers.packngo.filters.JwtUtil;
import com.packers.packngo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String email=loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        //Set the details in Security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok().body(LoginResponseDTO.builder()
                .Jwt(jwtUtil.generateToken(email, role))
                .role(role)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        try{
            registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ErrorResponseDTO
                            .builder()
                            .message("Please enter the password again")
                            .build()
                    );
        }
        return ResponseEntity.ok().body(userService.register(registerRequestDTO));
    }

}
