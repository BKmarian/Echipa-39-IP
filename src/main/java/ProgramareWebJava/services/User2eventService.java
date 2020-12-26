package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface User2eventService {

    Iterable<User2event> listAllUser2events();

    User2event saveUser2event(User2event ong);

    void deleteUser2event(Integer id);

    List<Event> getEventsbyPerson(Person person);
}
