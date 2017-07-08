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
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> listAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Integer id) {
        return personRepository.findOne(id);
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Integer id) {
        personRepository.delete(id);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.getPersonByUsername(username);
    }

    @Override
    public Person getPersonByEmail(String email) {
        return personRepository.getPersonByEmail(email);
    }
    /**
     * get events which user did not join yet
     */
    @Override
    public List<Event> getEventsNotJoined(Integer userid) {
        List<Event> eventsByPers = (ArrayList<Event>)user2eventService.getEventsbyPerson(this.getPersonById(userid));
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
