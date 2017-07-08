package com.ensat.repositories;

import com.ensat.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person getPersonByUsername(String username);
    Person getPersonByEmail(String email);
}
