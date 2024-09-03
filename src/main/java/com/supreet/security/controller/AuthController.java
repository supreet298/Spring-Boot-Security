package com.supreet.security.controller;

import com.supreet.security.dto.JwtResponse;
import com.supreet.security.dto.LoginForm;
import com.supreet.security.service.JwtService;
import com.supreet.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/home")
    public String handleHome() {
        return "Welcome Home";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/home")
    public String handleAdmin() {
        return "Admin Home";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','USER')")
    @GetMapping("user/home")
    public String handleUser() {
        return "User Home";
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.email(), loginForm.password()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(userService.loadUserByUsername(loginForm.email()));
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse("Inactive user"));
        }
    }
}
