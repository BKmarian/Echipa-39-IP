package ProgramareWebJava.repositories;

import ProgramareWebJava.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> getEventsByOng(Ong ong);

    @Query("select e from Event e order by e.date desc")
    Iterable<Event> findsLastFIVEvents();
}
