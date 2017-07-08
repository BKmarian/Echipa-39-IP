package com.ensat.controllers;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;
import com.ensat.entities.Person;
import com.ensat.entities.User2event;
import com.ensat.services.EventService;
import com.ensat.services.OngService;
import com.ensat.services.PersonService;
import com.ensat.services.User2eventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class AdminController {

    @Autowired
    private EventService eventService;

    @Autowired
    private OngService ongService;

    @Autowired
    private PersonService personService;

    @Autowired
    private User2eventService user2eventService;

    @RequestMapping("admin")
    public String adminIndex(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("admin",true);
        return "adminIndex";
    }

    @RequestMapping("admin/deleteEvent/{eventid}")
    public String deleteEvent(@PathVariable Integer eventid, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        //delete on cascade
        deleteUser2EventsByEvent(eventService.getEventById(eventid));
        eventService.deleteEvent(eventid);
        return "adminevents";

    }
    @RequestMapping("admin/deleteOng/{ongId}")
    public String deleteOng(@PathVariable Integer ongId, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        //delete in cascade
        deleteEvents(ongService.getOngById(ongId));
        ongService.deleteOng(ongId);
        return "adminongs";

    }
    @RequestMapping("admin/deletePerson/{personId}")
    public String deletePerson(@PathVariable Integer personId, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        //delete in cascade
        deleteUser2EventsByPerson(personService.getPersonById(personId));
        personService.deletePerson(personId);
        return "adminpersons";
    }

    @RequestMapping("admin/persons")
    public String persons(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        model.addAttribute("persons",personService.listAllPersons());
        return "adminpersons";
    }

    @RequestMapping("admin/events")
    public String events(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        model.addAttribute("events",eventService.sortEventsByDate( (ArrayList<Event>)eventService.listAllEvents()));
        return "adminevents";
    }

    @RequestMapping("admin/event/{eventid}")
    public String event(@PathVariable Integer eventid,HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        model.addAttribute("event", eventService.getEventById(eventid));
        return "showeventadmin";
    }

    @RequestMapping("admin/ongs")
    public String ongs(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        model.addAttribute("ongs",(ArrayList<Ong>)ongService.findOngsAccepted());
        return "adminongs";
    }

    @RequestMapping("admin/acceptong")
    public String acceptOng(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);

        System.out.println("LISSSSTTAAAA");
        System.out.println((ArrayList<Ong>)ongService.findOngsToAccept());

        model.addAttribute("ongs",(ArrayList<Ong>)ongService.findOngsToAccept());
        return "acceptongs";
    }
    @RequestMapping(value = "admin/acceptong/{ongId}")
    public String acceptOngPost(@PathVariable Integer ongId , Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(checkIsAdmin(session) != null)
            return checkIsAdmin(session);
        Ong ong = ongService.getOngById(ongId);
        ong.setApproved(true);
        ongService.saveOng(ong);
        model.addAttribute("ongs",(ArrayList<Ong>)ongService.findOngsToAccept());
        return "acceptongs";
    }

    public String checkIsAdmin(HttpSession session) {

        if(session.getAttribute("admin") == null)
            return "redirect:/login";
        return null;
    }
    public void deleteEvents(Ong ong) {
        for(Event event : eventService.getEventsByOng(ong)) {
            deleteUser2EventsByEvent(event);
            eventService.deleteEvent(event.getId());
        }
    }
    public void deleteUser2EventsByPerson(Person user) {
        ArrayList<User2event> lista = (ArrayList<User2event>)user2eventService.listAllUser2events();
        for(User2event user2event : lista) {
            if(user2event.getPerson().getId() == user.getId())
                user2eventService.deleteUser2event(user2event.getId());
        }
    }

    public void deleteUser2EventsByEvent(Event event) {
        ArrayList<User2event> lista = (ArrayList<User2event>)user2eventService.listAllUser2events();
        for(User2event user2event : lista) {
            if(user2event.getEvent().getId() == event.getId())
                user2eventService.deleteUser2event(user2event.getId());
        }
    }
}
