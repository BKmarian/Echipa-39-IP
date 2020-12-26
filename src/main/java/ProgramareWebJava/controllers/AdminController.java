package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class AdminController {

    @Autowired
    private EventService eventService;

    @Autowired
    private OngService ongService;

    @Autowired
    private PersonService personService;

    @Autowired
    private User2eventService user2eventService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/admin/deleteEvent/{eventid}")
    @ResponseBody
    public Event deleteEvent(@PathVariable Integer eventid) {
        Optional<Event> event = eventService.getEventById(eventid);
        event.ifPresent(this::deleteUser2EventsByEvent);
        eventService.deleteEvent(eventid);
        return event.get();

    }

    @DeleteMapping("/admin/deleteOng/{ongId}")
    @ResponseBody
    public Ong deleteOng(@PathVariable Integer ongId) {
        Ong ong = (Ong) userService.getUserById(ongId);
        deleteEvents(ong);
        userService.deleteUser(ongId);
        return ong;

    }

    @DeleteMapping("/admin/deletePerson/{personId}")
    @ResponseBody
    public Person deletePerson(@PathVariable Integer personId) {
        Person user = (Person) userService.getUserById(personId);
        deleteUser2EventsByPerson(user);
        userService.deleteUser(personId);
        return user;
    }

    @GetMapping("/admin/persons")
    @ResponseBody
    public List<Person> persons() {
        return StreamSupport
                .stream(personService.listAllPersons().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/admin/events")
    @ResponseBody
    public List<Event> events() {
        return eventService.sortEventsByDate(StreamSupport
                .stream(eventService.listAllEvents().spliterator(), false)
                .collect(Collectors.toList()));
    }

    @GetMapping("/admin/event/{eventid}")
    @ResponseBody
    public Event event(@PathVariable Integer eventid) {
        return eventService.getEventById(eventid).get();
    }

    @GetMapping("/admin/ongs")
    @ResponseBody
    public List<Ong> ongs() {
        return StreamSupport
                .stream(ongService.findOngsAccepted().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/admin/acceptong")
    @ResponseBody
    public List<Ong> toAcceptOng() {

        return StreamSupport
                .stream(ongService.findOngsToAccept().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/admin/acceptong/{ongId}")
    @ResponseBody
    public Ong acceptOngPost(@PathVariable Integer ongId) {
        Ong ong = (Ong) userService.getUserById(ongId);
        ong.setApproved(true);
        userService.saveUser(ong);
        return ong;
    }

    public void deleteEvents(Ong ong) {
        for (Event event : eventService.getEventsByOng(ong)) {
            deleteUser2EventsByEvent(event);
            eventService.deleteEvent(event.getId());
        }
    }

    public void deleteUser2EventsByPerson(Person user) {
        List<User2event> lista = (ArrayList<User2event>) user2eventService.listAllUser2events();
        for (User2event user2event : lista) {
            if (user2event.getPerson().getId().equals(user.getId()))
                user2eventService.deleteUser2event(user2event.getId());
        }
    }

    public void deleteUser2EventsByEvent(Event event) {
        List<User2event> lista = (ArrayList<User2event>) user2eventService.listAllUser2events();
        for (User2event user2event : lista) {
            if (user2event.getEvent().getId().equals(event.getId()))
                user2eventService.deleteUser2event(user2event.getId());
        }
    }
}
