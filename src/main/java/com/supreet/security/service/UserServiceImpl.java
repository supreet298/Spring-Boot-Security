package com.supreet.security.service;

import com.supreet.security.dto.UserDTO;
import com.supreet.security.model.User;
import com.supreet.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            var userObj = user.get();
            if (!userObj.isActive()) {
                throw new UsernameNotFoundException("User is inactive");
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException("email");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserDTO> getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> updateUser(String uuid, User user) {
        return userRepository.findByUuid(uuid)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setActive(true);
                    existingUser.setRole(user.getRole());
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public boolean deactivateUser(String uuid) {
        
        Optional<User> userOpt = userRepository.findByUuid(uuid);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActive(false); // Set isActive to false instead of deleting
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        return dto;
    }
}