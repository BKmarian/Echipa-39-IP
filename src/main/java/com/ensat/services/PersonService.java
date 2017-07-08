package com.ensat.services;

import com.ensat.entities.Person;
import com.ensat.entities.Event;
import java.util.List;

public interface PersonService {

    Iterable<Person> listAllPersons();


    Person getPersonById(Integer id);

    Person savePerson(Person person);

    void deletePerson(Integer id);

    List<Event> getEventsNotJoined(Integer id);

    Person getPersonByUsername(String username);

    Person getPersonByEmail(String email);
}
