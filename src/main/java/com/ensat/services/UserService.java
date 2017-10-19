package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    Iterable<User> listAllUsers();

    User getUserById(Integer id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void saveUser(User user);

    void deleteUser(Integer id);
}
