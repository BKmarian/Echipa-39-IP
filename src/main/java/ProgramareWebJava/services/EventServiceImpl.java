package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Iterable<Event> listAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
    }

    @Override
    public List<Event> getEventsByOng(Ong ong) {
        return eventRepository.getEventsByOng(ong);
    }

    @Override
    public List<Event> sortEventsByDate(List<Event> lista) {
        Collections.sort(lista, Comparator.comparing(Event::getDate));
        return lista;
    }

    @Override
    public List<Event> findsLastFIVEvents() {
        List<Event> lista = (ArrayList<Event>)(eventRepository.findsLastFIVEvents());
        return lista.subList(0,Math.min(lista.size(), 5));
    }
}
