package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.Event;
import ProgramareWebJava.entities.Location;
import ProgramareWebJava.entities.Ong;
import ProgramareWebJava.entities.UserToEvent;
import ProgramareWebJava.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    private UserToEventService userToEventService;

    @Autowired
    private UserService userService;

    public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping(value = "/updateOng")
    @ResponseBody
    @ApiOperation(
            value = "Update ong fields",
            response = String.class)
    public ResponseEntity<String> updateONG(@ApiParam("The ong parameters") @Valid @RequestBody Ong ong) {
        userService.saveUser(ong);
        return new ResponseEntity<>("Ong with id: " + ong.getUsername() + " updated ", HttpStatus.OK);
    }

    @DeleteMapping("/event/delete/{eventid}")
    @ResponseBody
    @ApiOperation(
            value = "Delete event created by ong",
            response = String.class)
    public ResponseEntity<String> deleteEvent(@ApiParam("The id of the event") @PathVariable Integer eventid) {
        deleteUser2Event(eventid);
        eventService.deleteEvent(eventid);
        return new ResponseEntity<>("Deleted event with id:" + eventid, HttpStatus.OK);
    }

    public void deleteUser2Event(Integer eventId) {
        for (UserToEvent u : userToEventService.listAllUser2events()) {
            if (u.getEvent().getId().equals(eventId))
                userToEventService.deleteUser2event(u.getId());
        }
    }


    @PostMapping(value = "/ong/event")
    @ResponseBody
    @ApiOperation(
            value = "Create event by ong with given fields",
            response = Event.class)
    public ResponseEntity<Event> saveEvent(HttpServletRequest request,@ApiParam("The parameters of the event")  @RequestParam Map<String, String> parameters) throws ParseException {

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

    @PostMapping("/ong/event/update")
    @ResponseBody
    @ApiOperation(
            value = "Update event by ong with given fields",
            response = String.class)
    public ResponseEntity<String> update(@ApiParam("The parameters of the event") @Valid @RequestBody Event event) {
        eventService.saveEvent(event);
        return new ResponseEntity<>("Event successfully updated", HttpStatus.OK);
    }

    @GetMapping("/ong/myEvents")
    @ResponseBody
    @ApiOperation(
            value = "Display Events by the logged in ong",
            response = List.class)
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
