package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    User2eventService user2eventService;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> listAllPersons() {
        return  StreamSupport.stream(personRepository.findAll().spliterator(), false).filter(p -> !p.getIsadmin()).collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsJoined(String username) {
        List<Event> eventsByPers = user2eventService.getEventsbyPerson((Person)userService.getUserByUsername(username));
        List<Event> allEvents = (ArrayList<Event>)eventService.listAllEvents();
        return allEvents.stream().filter(eventsByPers::contains).collect(Collectors.toList());
    }

}
