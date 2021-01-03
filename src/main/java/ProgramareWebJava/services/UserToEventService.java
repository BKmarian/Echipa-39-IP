package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserToEventService {

    Iterable<UserToEvent> listAllUser2events();

    UserToEvent saveUser2event(UserToEvent ong);

    void deleteUser2event(Integer id);

    List<Event> getEventsbyPerson(Person person);
}
