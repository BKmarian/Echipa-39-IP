package com.ensat.controllers;

import com.ensat.entities.Ong;
import com.ensat.entities.Person;
import com.ensat.services.OngService;
import com.ensat.services.PersonService;
import com.ensat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NewUsersController {

    @Autowired
    private PersonService personService;

    @Autowired
    private OngService ongService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createperson")
    public String returnView() {
        return "createperson";
    }

    @RequestMapping(value = "/createong")
    public String returnView2() {
        return "createong";
    }

    @RequestMapping(value = "/createperson", method = RequestMethod.POST)
    public String create_person(HttpServletRequest req, Model model) {
        String username = req.getParameter("usernameperson");
        if (!checkUsername(username)) {
            Person person = new Person.Builder()
                    .withAge(req.getParameter("age"))
                    .withFullName(req.getParameter("fullname"))
                    .withPhone(req.getParameter("phone"))
                    .withJob(req.getParameter("job"))
                    .withPassword(req.getParameter("passwordperson"))
                    .withUsername(username)
                    .withEmail(req.getParameter("email"))
                    .withIsadmin(false)
                    .build();
            userService.saveUser(person);
            model.addAttribute("error", "OK");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "createperson";
        }
    }

    @RequestMapping(value = "/createong", method = RequestMethod.POST)
    public String create_ong(HttpServletRequest req, Model model) {
        String username = req.getParameter("usernameong");
        if (!checkUsername(username)) {
            Ong ong = new Ong.Builder()
                    .withApproved(false)
                    .withContact(req.getParameter("contact"))
                    .withUsername(username)
                    .withEmail(req.getParameter("email"))
                    .withFullName(req.getParameter("fullname"))
                    .withPassword(req.getParameter("passwordong"))
                    .withPhone(req.getParameter("phone"))
                    .withIsadmin(false)
                    .build();
            userService.saveUser(ong);
            model.addAttribute("error", "OK");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "createong";
        }
    }

    public boolean checkUsername(String username) {
        return userService.getUserByUsername(username) != null;
    }

}
