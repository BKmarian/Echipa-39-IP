package ProgramareWebJava.services;

import ProgramareWebJava.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserById(Integer id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void saveUser(User user);

    void deleteUser(Integer id);

    void deleteAll();
}
