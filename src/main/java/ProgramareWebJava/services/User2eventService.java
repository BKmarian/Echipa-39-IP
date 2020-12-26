package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface User2eventService {

    Iterable<User2event> listAllUser2events();

    Optional<User2event> getUser2eventById(Integer id);

    User2event saveUser2event(User2event ong);

    void deleteUser2event(Integer id);

    List<Event> getEventsbyPerson(Person person);
}
