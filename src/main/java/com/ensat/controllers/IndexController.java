package com.ensat.controllers;

import com.ensat.entities.Ong;
import com.ensat.entities.Person;
import com.ensat.entities.User;
import com.ensat.services.EventService;
import com.ensat.services.OngService;
import com.ensat.services.PersonService;
import com.ensat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class IndexController {

    @Autowired
    PersonService personService;

    @Autowired
    OngService ongService;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @RequestMapping("/person/{id}")
    public String personIndex(Model model,HttpServletRequest request, @PathVariable Integer id) {
        System.out.println("version: " + SpringVersion.getVersion());
        HttpSession session = request.getSession();
        session.setAttribute("userid",id);
        model.addAttribute("userid",id);
        return "personindex";
    }

    @RequestMapping("/ong/{id}")
    public String ongIndex(Model model,HttpServletRequest request, @PathVariable Integer id) {
        HttpSession session = request.getSession();
        session.setAttribute("ongid",id);
        model.addAttribute("ongid",id);
        return "ongindex";
    }

    @RequestMapping("/allevents")
    public String guestevents(Model model) {
        model.addAttribute("events",eventService.listAllEvents());
        return "guestevents";
    }

    @RequestMapping("/showevent/{id}")
    public String showevent(Model model, @PathVariable Integer id) {
        model.addAttribute("event",eventService.getEventById(id));
        return "eventshow";
    }

    @RequestMapping(value = {"/login","/"}, method = RequestMethod.GET)
    public String loginpage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();

        System.out.println(eventService.findsLastFIVEvents());
        ArrayList<Integer> lista = eventService.findsLastFIVEvents();
        try {
            session.setAttribute("a", lista.get(0));
            session.setAttribute("b", lista.get(1));
            session.setAttribute("c", lista.get(2));
            session.setAttribute("d", lista.get(3));
            session.setAttribute("e", lista.get(4));
        }
        catch(Exception e) {
            return "login";
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model,HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + "   " + password);
        System.out.println(userService.listAllUsers());
        try {
            if (username.equals("admin") && password.equals("admin"))
                return "redirect:/admin";
            User user = userService.getUserByUsername(username);
            if (user != null) {
                System.out.println(user.getPassword());
                if (user.getPassword().equals(password) == true) {
                    // if (user.getIsAdmin() == true)
                    //return "redirect:/admin";

                    if (user instanceof Person)
                        return "redirect:/person/" + user.getId();
                    else
                        return "redirect:/ong/" + user.getId();

                }
            }
            return "login";
        }
        catch(Exception e){
                e.printStackTrace();
                return "login";
            }
        }
}
