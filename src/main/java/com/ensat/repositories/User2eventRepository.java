package com.ensat.repositories;

import com.ensat.entities.User2event;
import com.ensat.entities.Person;
import com.ensat.entities.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface User2eventRepository extends CrudRepository<User2event, Integer> {
    List<User2event> getEventsByPerson(Person person);
}
