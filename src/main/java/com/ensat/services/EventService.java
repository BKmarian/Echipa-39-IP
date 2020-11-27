package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EventService {

    Iterable<Event> listAllEvents();

    Optional<Event> getEventById(Integer id);

    Event saveEvent(Event ong);

    void deleteEvent(Integer id);

    List<Event> getEventsByOng(Ong ong);

    List<Event> sortEventsByDate(ArrayList<Event> lista);

    List<Integer> findsLastFIVEvents();
}
