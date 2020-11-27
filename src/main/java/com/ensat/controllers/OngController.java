package com.ensat.controllers;

import com.ensat.entities.Event;
import com.ensat.entities.Ong;
import com.ensat.entities.User2event;
import com.ensat.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
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
    private StorageService storageService;

    @Autowired
    private UserService userService;

    public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("ong/profile")
    public String profile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        Integer ongId = Integer.parseInt(session.getAttribute("ongid").toString());
        model.addAttribute("ong", userService.getUserById(ongId));
        return "ongprofile";
    }

    /**
     * New event.
     */
    @RequestMapping("ong/createevent")
    public String newEvent(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
//        model.addAttribute("event", new Event());
        return "eventform";
    }

    @RequestMapping(value = "ong", method = RequestMethod.POST)
    public String updateONG(Ong ong, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        if (ong.getPassword() == null) {
            int ongid = Integer.parseInt(session.getAttribute("ongid").toString());
            Ong ong2 = (Ong) userService.getUserById(ongid);
            ong.setPassword(ong2.getPassword());
        }
        userService.saveUser(ong);
        return "redirect:/ong/" + ong.getId();
    }

    @RequestMapping("event/delete/{eventid}")
    public String deleteEvent(@PathVariable Integer eventid, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        deleteUser2Event(eventid);
        eventService.deleteEvent(eventid);
        return "redirect:/ong/myEvents";
    }

    public void deleteUser2Event(Integer eventId) {
        for (User2event u : user2eventService.listAllUser2events()) {
            if (u.getEvent().getId().equals(eventId))
                user2eventService.deleteUser2event(u.getId());
        }
    }


    /**
     * Save event to database.
     */
    @RequestMapping(value = "ong/event", method = RequestMethod.POST)
    public String saveEvent(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws ParseException {

        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        Ong ong = (Ong) userService.getUserById(Integer.parseInt(session.getAttribute("ongid").toString()));
        Integer ongId = Integer.parseInt(request.getSession().getAttribute("ongid").toString());
        System.out.println("ongId=" + ongId);
        Event event = new Event();
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String date = request.getParameter("date");

        if (date == null || date.equals("")) {
            model.addAttribute("error", "Date cannot be null");
            return "eventform";
        }

        Date data = FORMAT.parse(date);
        event.setDate(data);

        event.setDescription(description);
        event.setLocation(location);
        event.setName(name);
        event.setOng(ong);
        eventService.saveEvent(event);
        //Save image
        try {
            storageService.store(file, "event" + event.getId());
        } catch (Exception e) {
            return "redirect:/ong/event/" + event.getId();
        }
        return "redirect:/ong/event/" + event.getId();
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping("ong/event/{eventid}")
    public String event2(@PathVariable Integer eventid, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        model.addAttribute("event", eventService.getEventById(eventid));
        //model.addAttribute("ongid",ongid);
        return "showeventong";
    }

    @RequestMapping("event/edit/{id}")
    public String edit(@PathVariable Integer id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);

        session.setAttribute("eventid", id);

        model.addAttribute("event", eventService.getEventById(id));
        return "eventupdate";
    }

    @RequestMapping("/ong/event/update")
    public String update(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws ParseException {
        HttpSession session = request.getSession();
        Integer id = Integer.parseInt(request.getParameter("id"));
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        Event event = eventService.getEventById(id).get();
        event.setName(request.getParameter("name"));
        event.setDescription(request.getParameter("description"));
        event.setLocation(request.getParameter("location"));
        String date = request.getParameter("date");

        if (date == null || date.equals("")) {
            model.addAttribute("error", "Date cannot be null");
            return "eventform";
        }

        Date data = FORMAT.parse(date);
        event.setDate(data);
        eventService.saveEvent(event);
        model.addAttribute("event", event);
        try {
            deleteImage("src/main/resources/static/images/event" + event.getId());
            storageService.store(file, "event" + event.getId());
        } catch (Exception e) {
            return "redirect:/ong/event/" + event.getId();
        }
        return "showeventong";
    }

    public void deleteImage(String file) {
        File filee = new File(file);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        if (filee.delete()) {
            System.out.println(filee.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }

    }

    @RequestMapping("ong/myEvents")
    public String getEvents(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (checkIsOng(session) != null)
            return checkIsOng(session);
        Integer ongId = Integer.parseInt(request.getSession().getAttribute("ongid").toString());
        ArrayList<Event> events = (ArrayList<Event>) eventService.getEventsByOng((Ong) userService.getUserById(ongId));
        model.addAttribute("events", eventService.sortEventsByDate(events));
        return "ongevents";
    }

    public String checkIsOng(HttpSession session) {

        if (session.getAttribute("ongid") == null)
            return "redirect:/login";
        return null;
    }
}
