package com.ensat.services;

import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.entities.User2event;
import com.ensat.repositories.User2eventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<User2event> list = user2eventRepository.getEventsByPerson(person);
        return list.stream().map(User2event::getEvent).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Iterable<User2event> listAllUser2events() {
        return user2eventRepository.findAll();
    }

    @Override
    public Optional<User2event> getUser2eventById(Integer id) {
        return user2eventRepository.findById(id);
    }

    @Override
    public User2event saveUser2event(User2event user2event) {
        return user2eventRepository.save(user2event);
    }

    @Override
    public void deleteUser2event(Integer id) {
        user2eventRepository.deleteById(id);
    }

}
