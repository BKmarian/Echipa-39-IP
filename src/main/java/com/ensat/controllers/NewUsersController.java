package com.ensat.controllers;

import com.ensat.entities.Ong;
import com.ensat.entities.Person;
import com.ensat.entities.User;
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
    PersonService personService;

    @Autowired
    OngService ongService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/createperson")
    public String returnView() {
        return "createperson";
    }

    @RequestMapping(value = "/createong")
    public String returnView2() {
        return "createong";
    }

    @RequestMapping(value = "/createperson",method = RequestMethod.POST)
    public String create_person(HttpServletRequest req,Model model) {
        String username = req.getParameter("usernameperson");
        String password = req.getParameter("passwordperson");
        String fullName = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String job = req.getParameter("job");
        String age = req.getParameter("age");
        String email = req.getParameter("email");
        if (checkUsername(username) == false) {
            Person person = new Person();
            person.setAge(Integer.parseInt(age));
            person.setPassword(password);
            person.setEmail(email);
            person.setFullName(fullName);
            person.setJob(job);
            person.setPhone(phone);
            person.setUsername(username);
            userService.saveUser(person);
            model.addAttribute("error","OK");
            return "redirect:/login";
        }
        else {
            model.addAttribute("error","Username already exists");
            return "createperson";
        }
    }
    @RequestMapping(value =  "/createong",method = RequestMethod.POST)
    public String create_ong(HttpServletRequest req,Model model) {
        String username = req.getParameter("usernameong");
        String password = req.getParameter("passwordong");
        String fullName = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String contact = req.getParameter("contact");
        String email = req.getParameter("email");
        if(checkUsername(username) == false) {
            Ong ong = new Ong();
            ong.setContact(contact);
            ong.setPassword(password);
            ong.setEmail(email);
            ong.setFullName(fullName);
            ong.setContact(address);
            ong.setPhone(phone);
            ong.setUsername(username);
            ong.setApproved(false);
            userService.saveUser(ong);
            model.addAttribute("error","OK");
            return "redirect:/login";
        }
        else {
            model.addAttribute("error","Username already exists");
            return "createong";
        }
    }
    public boolean checkUsername(String username) {
        User user = userService.getUserByUsername(username);
        if(user == null)
            return false;
        return true;
    }

}
