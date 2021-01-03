package ProgramareWebJava.repositories;

import ProgramareWebJava.entities.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserToEventRepository extends CrudRepository<UserToEvent, Integer> {
    List<UserToEvent> getEventsByPerson(Person person);
}
