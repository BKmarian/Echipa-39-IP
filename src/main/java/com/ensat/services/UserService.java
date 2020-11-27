package com.ensat.services;

import com.ensat.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Iterable<User> listAllUsers();

    User getUserById(Integer id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void saveUser(User user);

    void deleteUser(Integer id);
}
