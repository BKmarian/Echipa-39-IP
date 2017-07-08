package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;

import java.util.ArrayList;
import java.util.List;

public interface EventService {

    Iterable<Event> listAllEvents();

    Event getEventById(Integer id);

    Event saveEvent(Event ong);

    void deleteEvent(Integer id);

    List<Event> getEventsByOng(Ong ong);

    List<Event> sortEventsByDate(ArrayList<Event> lista);

    ArrayList<Integer> findsLastFIVEvents();
}
