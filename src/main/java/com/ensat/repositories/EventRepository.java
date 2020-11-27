package com.ensat.repositories;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> getEventsByOng(Ong ong);

    @Query("select e from Event e order by e.date desc")
    Iterable<Event> findsLastFIVEvents();
}
