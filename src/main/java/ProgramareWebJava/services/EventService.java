package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EventService {

    Iterable<Event> listAllEvents();

    Optional<Event> getEventById(Integer id);

    Event saveEvent(Event ong);

    void deleteEvent(Integer id);

    void deleteAll();

    List<Event> getEventsByOng(Ong ong);

    List<Event> sortEventsByDate(List<Event> lista);

    List<Event> findsLastFIVEvents();
}
