package com.ensat.services;

import com.ensat.entities.Person;
import com.ensat.entities.Event;
import java.util.List;

public interface PersonService {

    Iterable<Person> listAllPersons();

    List<Event> getEventsNotJoined(Integer id);

}
