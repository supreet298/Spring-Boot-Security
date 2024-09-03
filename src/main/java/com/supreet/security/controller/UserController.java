package com.supreet.security.controller;

import com.supreet.security.dto.UserDTO;
import com.supreet.security.model.User;
import com.supreet.security.repository.UserRepository;
import com.supreet.security.service.JwtService;
import com.supreet.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @PostMapping("/createUser")
    public User createUser(@Valid @RequestBody User user, @RequestHeader("Authorization") String token) {
        // Extract the username from the token
        String username = jwtService.extractUsername(token.substring(7));

        // Load user details using the username
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Check if the user is an admin
        if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            // Ensure that only the Admin can create Manager or User roles
            if (user.getRole() == User.Role.MANAGER || user.getRole() == User.Role.USER) {
                // Set user as active by default
                user.setActive(true);
                // Encode the password
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                // Save and return the user

                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Invalid role specified.");
            }
        } else {
            throw new AccessDeniedException("Only admins can create users.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    public ResponseEntity<Optional<UserDTO>> getUserByUuid(@PathVariable String uuid) {
        Optional<UserDTO> users = userService.getUserByUuid(uuid);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{uuid}")
    public User updateUser(@PathVariable String uuid, @RequestBody User user) {
        return userService.updateUser(uuid, user).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteUserByUuid(@PathVariable String uuid) {
        boolean deactivated = userService.deactivateUser(uuid);
        if (deactivated) {
            return ResponseEntity.ok("User marked as inactive");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
