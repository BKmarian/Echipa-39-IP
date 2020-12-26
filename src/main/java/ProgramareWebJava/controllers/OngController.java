package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.Event;
import ProgramareWebJava.entities.Location;
import ProgramareWebJava.entities.Ong;
import ProgramareWebJava.entities.User2event;
import ProgramareWebJava.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
public class OngController {


    @Autowired
    private PersonService personService;

    @Autowired
    private OngService ongService;

    @Autowired
    private EventService eventService;

    @Autowired
    private User2eventService user2eventService;

    @Autowired
    private UserService userService;

    public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping(value = "/updateOng")
    @ResponseBody
    public ResponseEntity<String> updateONG(@Valid @RequestBody Ong ong) {
        userService.saveUser(ong);
        return new ResponseEntity<>("Ong with id: " + ong.getUsername() + " updated ", HttpStatus.OK);
    }

    @DeleteMapping("/event/delete/{eventid}")
    @ResponseBody
    public ResponseEntity<String> deleteEvent(@PathVariable Integer eventid) {
        deleteUser2Event(eventid);
        eventService.deleteEvent(eventid);
        return new ResponseEntity<>("Deleted event with id:" + eventid, HttpStatus.OK);
    }

    public void deleteUser2Event(Integer eventId) {
        for (User2event u : user2eventService.listAllUser2events()) {
            if (u.getEvent().getId().equals(eventId))
                user2eventService.deleteUser2event(u.getId());
        }
    }


    @PostMapping(value = "/ong/event")
    @ResponseBody
    public ResponseEntity<Event> saveEvent(HttpServletRequest request, @RequestParam Map<String, String> parameters) throws ParseException {

        HttpSession session = request.getSession();
        Ong ong = (Ong) userService.getUserByUsername(session.getAttribute("username").toString());
        Event event = Event.builder()
                .date(FORMAT.parse(parameters.get("date")))
                .description(parameters.get("description"))
                .location(createLocation(parameters))
                .name(parameters.get("name"))
                .ong(ong)
                .build();
        eventService.saveEvent(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping("/ong/event/update")
    @ResponseBody
    public ResponseEntity<String> update(@Valid @RequestBody Event event) {
        eventService.saveEvent(event);
        return new ResponseEntity<>("Event successfully updated", HttpStatus.OK);
    }

    @GetMapping("/ong/myEvents")
    @ResponseBody
    public ResponseEntity<List<Event>> getEvents(HttpServletRequest request) {
        String username = request.getSession().getAttribute("username").toString();
        return new ResponseEntity<>(eventService.getEventsByOng((Ong) userService.getUserByUsername(username)), HttpStatus.OK);
    }

    private Location createLocation(Map<String, String> parameters) {
        Location location = new Location();
        location.setAddress(parameters.get("address"));
        location.setPostalCode(parameters.get("postal_code"));
        return location;
    }

}
