package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.repositories.PersonRepository;
import com.ensat.repositories.User2eventRepository;
import com.ensat.repositories.UserBaseRepository;
import com.ensat.entities.User;
import com.ensat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public void setUserBaseRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserById(Integer id) {
        Object obj = userRepository.getUserById(id);
        if(obj == null)
            return null;
        else
            return (User) obj;
    }

    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        Object obj = userRepository.getUserByUsername(username);
        if (obj == null)
            return null;
        else
            return (User)obj;
    }

    public User getUserByEmail(String email) {
        Object user = userRepository.getUserByEmail(email);
        if (user == null)
            return null;
        else
            return (User)user;

    }

}
