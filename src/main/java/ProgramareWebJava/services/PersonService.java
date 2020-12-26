package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    Iterable<Person> listAllPersons();

    List<Event> getEventsJoined(String username);

}
