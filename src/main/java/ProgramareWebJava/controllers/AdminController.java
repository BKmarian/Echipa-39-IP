package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    private UserToEventService userToEventService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/admin/deleteOng/{ongId}")
    @ResponseBody
    @ApiOperation(
            value = "Delete ong user by given id",
            response = Ong.class)
    public ResponseEntity<Ong> deleteOng(@ApiParam("The id of the ong user") @PathVariable Integer ongId) {
        Ong ong = (Ong) userService.getUserById(ongId);
        deleteEvents(ong);
        userService.deleteUser(ongId);
        return new ResponseEntity<>(ong, HttpStatus.OK);

    }

    @DeleteMapping("/admin/deletePerson/{personId}")
    @ResponseBody
    @ApiOperation(
            value = "Delete person user by given id",
            response = Person.class)
    public ResponseEntity<Person> deletePerson(@ApiParam("The id of the person user") @PathVariable Integer personId) {
        Person user = (Person) userService.getUserById(personId);
        deleteUser2EventsByPerson(user);
        userService.deleteUser(personId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/persons")
    @ResponseBody
    @ApiOperation(
            value = "Get all person users as a list.",
            response = List.class)
    public ResponseEntity<List<Person>> persons() {
        return new ResponseEntity<>(StreamSupport
                .stream(personService.listAllPersons().spliterator(), false)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/admin/ongs")
    @ResponseBody
    @ApiOperation(
            value = "Get all ong users as a list.",
            response = List.class)
    public ResponseEntity<List<Ong>> ongs() {
        return new ResponseEntity<>(StreamSupport
                .stream(ongService.findOngsAccepted().spliterator(), false)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/acceptong/{ongId}")
    @ResponseBody
    @ApiOperation(
            value = "Admin accepts ong",
            response = String.class)
    public ResponseEntity<String> acceptOngPost(@ApiParam("The id of the ong user") @PathVariable Integer ongId) {
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
        ((ArrayList<UserToEvent>) userToEventService.listAllUser2events()).stream().filter(user2event -> user2event.getPerson().getId().equals(user.getId())).forEach(user2event -> userToEventService.deleteUser2event(user2event.getId()));
    }

    public void deleteUser2EventsByEvent(Event event) {
        ((ArrayList<UserToEvent>) userToEventService.listAllUser2events()).stream().filter(user2event -> user2event.getEvent().getId().equals(event.getId())).forEach(user2event -> userToEventService.deleteUser2event(user2event.getId()));
    }
}
