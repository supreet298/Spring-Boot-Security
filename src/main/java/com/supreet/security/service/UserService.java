package com.supreet.security.service;

import com.supreet.security.dto.UserDTO;
import com.supreet.security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    List<User> getAll();

    Optional<UserDTO> getUserByUuid(String uuid);

    List<UserDTO> getAllUsers();

    boolean deactivateUser(String uuid);

    Optional<User> updateUser(String uuid, User user);

}
