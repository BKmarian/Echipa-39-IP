package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;
import com.ensat.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public Event getEventById(Integer id) {
        return eventRepository.findOne(id);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Integer id) {
        eventRepository.delete(id);
    }

    @Override
    public List<Event> getEventsByOng(Ong ong) {
        return (List<Event>)eventRepository.getEventsByOng(ong);
    }

    @Override
    public List<Event> sortEventsByDate(ArrayList<Event> lista) {
        Collections.sort(lista, new Comparator<Event>() {
            @Override
            public int compare(Event a1, Event a2) {
                try {
                    if(a1.getDate().compareTo(a2.getDate()) > 0)
                        return -1;
                    else
                        return 1;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 1;
                }
            }
        });
        return lista;
    }

    @Override
    public ArrayList<Integer> findsLastFIVEvents() {
        ArrayList<Event> lista = (ArrayList<Event>)(eventRepository.findsLastFIVEvents());
        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        int lungime;
        if(lista.size() < 5)
            lungime = lista.size();
        else
            lungime = 5;
        for(int i = 0 ; i < lungime ; i++)
            lista2.add(lista.get(i).getId());
        return lista2;
    }
}
