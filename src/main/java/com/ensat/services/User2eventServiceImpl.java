package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.entities.User2event;
import com.ensat.repositories.User2eventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User2event service implement.
 */
@Service
public class User2eventServiceImpl implements User2eventService {

    private User2eventRepository user2eventRepository;

    @Autowired
    public void setUser2eventRepository(User2eventRepository user2eventRepository) {
        this.user2eventRepository = user2eventRepository;
    }

    @Override
    public List<Event> getEventsbyPerson(Person person) {
        ArrayList<User2event> list = (ArrayList<User2event>) (user2eventRepository.getEventsByPerson(person));
        ArrayList<Event> eventList = new ArrayList<Event>();
        for (User2event user2event : list)
            eventList.add(user2event.getEvent());
        return eventList;
    }

    @Override
    public Iterable<User2event> listAllUser2events() {
        return user2eventRepository.findAll();
    }

    @Override
    public User2event getUser2eventById(Integer id) {
        return user2eventRepository.findOne(id);
    }

    @Override
    public User2event saveUser2event(User2event user2event) {
        return user2eventRepository.save(user2event);
    }

    @Override
    public void deleteUser2event(Integer id) {
        user2eventRepository.delete(id);
    }

}
