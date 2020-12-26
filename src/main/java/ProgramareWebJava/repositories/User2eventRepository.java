package ProgramareWebJava.repositories;

import ProgramareWebJava.entities.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface User2eventRepository extends CrudRepository<User2event, Integer> {
    List<User2event> getEventsByPerson(Person person);
}
