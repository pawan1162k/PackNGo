package com.packers.packngo.controller.admin;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Users;
import com.packers.packngo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Users> getUser() throws ResourceNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PutMapping("/")
    public ResponseEntity<Users> updateUser(@RequestBody Users user) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }
}
