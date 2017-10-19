package com.ensat.controllers;

import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.entities.User2event;
import com.ensat.services.EventService;
import com.ensat.services.PersonService;
import com.ensat.services.User2eventService;
import com.ensat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Person controller.
 */
@Controller
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

    @RequestMapping("person/profile")
    public String profile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        model.addAttribute("person", userService.getUserById(userid));
        return "personprofile";
    }

    @RequestMapping("person/myevents")
    public String myevents(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        model.addAttribute("events", eventService.sortEventsByDate((ArrayList<Event>)user2eventService.getEventsbyPerson((Person)userService.getUserById(userid))));
        model.addAttribute("userid",userid);
        return "personevents";
    }
    @RequestMapping("person/event/{eventid}")
    public String event(@PathVariable Integer eventid,HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        //Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        model.addAttribute("event", eventService.getEventById(eventid));
        //model.addAttribute("userid",userid);
        return "showeventperson";
    }

    @RequestMapping("person/event/delete/{eventid}")
    public String deleteuser2event(@PathVariable Integer eventid,HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        //Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        deleteuser2event(eventid,userid);
        //model.addAttribute("userid",userid);
        return "redirect:/person/myevents";
    }

    public void deleteuser2event(int eventid,int userid) {
        for(User2event u : user2eventService.listAllUser2events()) {
            if(u.getEvent().getId() == eventid && u.getPerson().getId() == userid)
                user2eventService.deleteUser2event(u.getId());
        }
    }
    @RequestMapping("person/allevents")
    public String allevents(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        model.addAttribute("events", eventService.sortEventsByDate((ArrayList<Event>)personService.getEventsNotJoined(userid)));
        model.addAttribute("userid",userid);
        return "allevents";
    }
    @RequestMapping("person/joinevent/{eventid}")
    public String addEvent(HttpServletRequest request, @PathVariable Integer eventid, Model model) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        Person person = (Person)userService.getUserById(userid);
        Event event = eventService.getEventById(eventid);
        user2eventService.saveUser2event(new User2event(person,event));
        return "redirect:/person/allevents";
    }

    /**
     * Save person to database.
     */
    @RequestMapping(value = "person", method = RequestMethod.POST)
    public String savePerson(Person person,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (checkIsPerson(session) != null)
            return checkIsPerson(session);
        if(person.getPassword() == null) {
            int userId = Integer.parseInt(session.getAttribute("userid").toString());
            Person p = (Person)userService.getUserById(userId);
            person.setPassword(p.getPassword());
        }
        System.out.println(person);
        userService.saveUser(person);
        return "redirect:/person/" + person.getId();
    }

    public String checkIsPerson(HttpSession session) {

        if(session.getAttribute("userid") == null)
            return "redirect:/login";
        return null;
    }
}
