package com.ensat.repositories;

import com.ensat.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PersonRepository extends UserBaseRepository<Person> {
}
