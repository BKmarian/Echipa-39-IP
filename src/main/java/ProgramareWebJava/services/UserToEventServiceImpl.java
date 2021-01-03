package ProgramareWebJava.services;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.repositories.UserToEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserToEventServiceImpl implements UserToEventService {

    private UserToEventRepository userToeventRepository;

    @Autowired
    public void setUser2eventRepository(UserToEventRepository userToeventRepository) {
        this.userToeventRepository = userToeventRepository;
    }

    @Override
    public List<Event> getEventsbyPerson(Person person) {
        List<UserToEvent> list = userToeventRepository.getEventsByPerson(person);
        return list.stream().map(UserToEvent::getEvent).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Iterable<UserToEvent> listAllUser2events() {
        return userToeventRepository.findAll();
    }

    @Override
    public UserToEvent saveUser2event(UserToEvent userToEvent) {
        return userToeventRepository.save(userToEvent);
    }

    @Override
    public void deleteUser2event(Integer id) {
        userToeventRepository.deleteById(id);
    }

}
