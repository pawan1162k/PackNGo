package com.packers.packngo.service;

import com.packers.packngo.ExceptionHandler.DatabaseSaveException;
import com.packers.packngo.ExceptionHandler.InvalidCredentialsException;
import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.ExceptionHandler.UserNotFoundException;
import com.packers.packngo.dto.RegisterRequestDTO;
import com.packers.packngo.dto.SuccessResponseDTO;
import com.packers.packngo.model.Users;
import com.packers.packngo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public SuccessResponseDTO register(RegisterRequestDTO registerRequestDTO){
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new UserNotFoundException("Username already exist. please use different username");
        }

        try {
            // Create new User
            // Save User to DB
            if(registerRequestDTO.getRole().equals("ADMIN")) {
                if(registerRequestDTO.getSecurityToken()==null ){
                    throw new InvalidCredentialsException("Please enter the admin code");
                }else if(registerRequestDTO.getSecurityToken().equals("ADMINCODE")) {
                    userRepository.save(Users
                            .builder()
                            .username(registerRequestDTO.getUsername())
                            .password(registerRequestDTO.getPassword())
                            .role(registerRequestDTO.getRole())
                            .DLNO(registerRequestDTO.getDLNO())
                            .phone(registerRequestDTO.getPhone())
                            .name(registerRequestDTO.getName())
                            .build());
                }else{
                    throw new InvalidCredentialsException("Enter the correct Admin code to create Admin account");
                }
            }else if(registerRequestDTO.getRole().equals("USER")){
                userRepository.save(Users
                        .builder()
                        .username(registerRequestDTO.getUsername())
                        .password(registerRequestDTO.getPassword())
                        .role(registerRequestDTO.getRole())
                        .DLNO(registerRequestDTO.getDLNO())
                        .phone(registerRequestDTO.getPhone())
                        .name(registerRequestDTO.getName())
                        .build());
            }else{
                throw new InvalidCredentialsException("Enter the correct details to create User");
            }

        }catch (DatabaseSaveException e){
            throw new DatabaseSaveException(e.getMessage());
        }

        return SuccessResponseDTO
                .builder()
                .data( "User registered successfully")
                .status(HttpStatus.OK)
                .build();

    }


    public Users getUser(String username) throws ResourceNotFoundException {
        // Fetch the user by ID or throw an exception if not found
        Optional<Users> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + username));
    }

    public Users updateUser(Users updatedUser) throws ResourceNotFoundException {
        // Check if the user exists
        Optional<Users> existingUser = userRepository.findById(updatedUser.getId());
        if (existingUser.isPresent()) {
            // Update user details
            Users user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setRole(updatedUser.getRole()); // Assuming role is a field
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + updatedUser.getId());
        }
    }

}
