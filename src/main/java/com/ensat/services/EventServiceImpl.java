package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;
import com.ensat.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Event service implement.
 */
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
    public List<Event> getEventsByOng(Ong ong) {
        return eventRepository.getEventsByOng(ong);
    }

    @Override
    public List<Event> sortEventsByDate(ArrayList<Event> lista) {
        Collections.sort(lista, Comparator.comparing(Event::getDate));
        return lista;
    }

    @Override
    public List<Integer> findsLastFIVEvents() {
        List<Event> lista = (ArrayList<Event>)(eventRepository.findsLastFIVEvents());
        int lungime = Math.min(lista.size(), 5);
        return lista.subList(0,lungime).stream().map(Event::getId).collect(Collectors.toList());
    }
}
