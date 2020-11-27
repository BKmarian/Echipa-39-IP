package com.ensat.services;

import com.ensat.entities.User2event;
import com.ensat.entities.Person;
import com.ensat.entities.Event;

import java.util.List;
import java.util.Optional;

public interface User2eventService {

    Iterable<User2event> listAllUser2events();

    Optional<User2event> getUser2eventById(Integer id);

    User2event saveUser2event(User2event ong);

    void deleteUser2event(Integer id);

    List<Event> getEventsbyPerson(Person person);
}
