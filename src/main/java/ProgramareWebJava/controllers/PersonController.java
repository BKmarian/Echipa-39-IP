package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.Event;
import ProgramareWebJava.entities.Person;
import ProgramareWebJava.entities.User2event;
import ProgramareWebJava.services.EventService;
import ProgramareWebJava.services.PersonService;
import ProgramareWebJava.services.User2eventService;
import ProgramareWebJava.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    private User2eventService user2eventService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/person/profile")
    @ResponseBody
    @ApiOperation(
            value = "Show person profile",
            notes = "Show logged in person profile",
            response = String.class)
    public ResponseEntity<Person> profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        return new ResponseEntity<>((Person) userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping(value = "/updatePerson")
    @ResponseBody
    @ApiOperation(
            value = "Person fields",
            notes = "Update person fields",
            response = String.class)
    public ResponseEntity<String> updatePerson(@ApiParam("The person parameters") @Valid @RequestBody Person person) {
        userService.saveUser(person);
        return new ResponseEntity<>("Person with id: " + person.getUsername() + " updated ", HttpStatus.OK);
    }

    @DeleteMapping("/person/event/delete/{eventid}")
    @ResponseBody
    @ApiOperation(
            value = "Get event by id",
            notes = "Remove event joined by person",
            response = String.class)
    public ResponseEntity<String> deleteUser2event(@ApiParam("The event id") @PathVariable Integer eventid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        try {
            deleteUser2event(eventid, username);
            return new ResponseEntity<>("Deleted user " + username, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user " + username, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public void deleteUser2event(int eventid, String username) {
        for (User2event u : user2eventService.listAllUser2events()) {
            if (u.getEvent().getId() == eventid && u.getPerson().getUsername().equals(username))
                user2eventService.deleteUser2event(u.getId());
        }
    }

    @GetMapping("/person/allevents")
    @ResponseBody
    @ApiOperation(
            value = "Get user events",
            notes = "Show events joined by person logged in",
            response = List.class)
    public ResponseEntity<List<Event>> allevents(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        return new ResponseEntity<>(eventService.sortEventsByDate(personService.getEventsJoined(username)), HttpStatus.OK);
    }

    @PutMapping("/person/joinevent/{eventid}")
    @ResponseBody
    @ApiOperation(
            value = "Get event by id",
            notes = "Person user joins event",
            response = Event.class)
    public ResponseEntity<Event> addEvent(HttpServletRequest request, @ApiParam("The event of the id") @PathVariable Integer eventid) {
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        Person person = (Person) userService.getUserByUsername(username);
        Event event = eventService.getEventById(eventid).get();
        user2eventService.saveUser2event(new User2event(person, event));
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

}
