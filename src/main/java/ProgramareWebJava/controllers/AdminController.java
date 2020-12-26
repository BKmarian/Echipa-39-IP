package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @DeleteMapping("/admin/deleteOng/{ongId}")
    @ResponseBody
    public ResponseEntity<Ong> deleteOng(@PathVariable Integer ongId) {
        Ong ong = (Ong) userService.getUserById(ongId);
        deleteEvents(ong);
        userService.deleteUser(ongId);
        return new ResponseEntity<>(ong, HttpStatus.OK);

    }

    @DeleteMapping("/admin/deletePerson/{personId}")
    @ResponseBody
    public ResponseEntity<Person> deletePerson(@PathVariable Integer personId) {
        Person user = (Person) userService.getUserById(personId);
        deleteUser2EventsByPerson(user);
        userService.deleteUser(personId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/persons")
    @ResponseBody
    public ResponseEntity<List<Person>> persons() {
        return new ResponseEntity<>(StreamSupport
                .stream(personService.listAllPersons().spliterator(), false)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/admin/ongs")
    @ResponseBody
    public ResponseEntity<List<Ong>> ongs() {
        return new ResponseEntity<>(StreamSupport
                .stream(ongService.findOngsAccepted().spliterator(), false)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/acceptong/{ongId}")
    @ResponseBody
    public ResponseEntity<String> acceptOngPost(@PathVariable Integer ongId) {
        Ong ong = (Ong) userService.getUserById(ongId);
        ong.setApproved(true);
        userService.saveUser(ong);
        return new ResponseEntity<>("Ong with id " + ong.getId() + " was accepted", HttpStatus.OK);
    }

    public void deleteEvents(Ong ong) {
        eventService.getEventsByOng(ong).forEach(event -> {
            deleteUser2EventsByEvent(event);
            eventService.deleteEvent(event.getId());
        });
    }

    public void deleteUser2EventsByPerson(Person user) {
        ((ArrayList<User2event>) user2eventService.listAllUser2events()).stream().filter(user2event -> user2event.getPerson().getId().equals(user.getId())).forEach(user2event -> user2eventService.deleteUser2event(user2event.getId()));
    }

    public void deleteUser2EventsByEvent(Event event) {
        ((ArrayList<User2event>) user2eventService.listAllUser2events()).stream().filter(user2event -> user2event.getEvent().getId().equals(event.getId())).forEach(user2event -> user2eventService.deleteUser2event(user2event.getId()));
    }
}
