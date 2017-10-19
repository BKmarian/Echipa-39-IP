package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Person service implement.
 */
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
        return personRepository.findAll();
    }

    /**
     * get events which user did not join yet
     */
    @Override
    public List<Event> getEventsNotJoined(Integer userid) {
        List<Event> eventsByPers = (ArrayList<Event>)user2eventService.getEventsbyPerson((Person)userService.getUserById(userid));
        List<Event> allEvents = (ArrayList<Event>)eventService.listAllEvents();
        List<Event> returnList = new ArrayList<Event>();
        for(Event event : allEvents) {
            if(eventsByPers.contains(event) == false)
                returnList.add(event);
        }
        System.out.println(returnList.toString());
        return returnList;
    }

}
