package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.repositories.User2eventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public User2event saveUser2event(User2event user2event) {
        return user2eventRepository.save(user2event);
    }

    @Override
    public void deleteUser2event(Integer id) {
        user2eventRepository.deleteById(id);
    }

}
