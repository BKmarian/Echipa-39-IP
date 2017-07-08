package com.ensat.controllers;

import com.ensat.configuration.SmtpMailSender;
import com.ensat.entities.Ong;
import com.ensat.entities.Person;
import com.ensat.services.OngService;
import com.ensat.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ChangePassController {

    @Autowired
    PersonService personService;

    @Autowired
    OngService ongService;

    final String fromEmail = "cosminmanolescudans@gmail.com";

    @RequestMapping("/person/changepwd")
    public String change1(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userid") == null)
            return "redirect:/login";
        return "personchangepass";
    }
    @RequestMapping("/ong/changepwd")
    public String change2(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ongid") == null)
            return "redirect:/login";
        return "ongchangepass";
    }
    @RequestMapping(value = "/person/changepassword", method = RequestMethod.POST)
    public String personchangepasword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userid") == null)
            return "redirect:/login";
        Person person = personService.getPersonById(Integer.parseInt(session.getAttribute("userid").toString()));
        String password = request.getParameter("password");
        String verifypassword = request.getParameter("verifypassword");
        String newpassword = request.getParameter("newpassword");
        if(password.equals(verifypassword) == false)
            return "redirect:/person/" + person.getId();
        else {
            person.setPassword(newpassword);
            personService.savePerson(person);
            return "redirect:/person/" + person.getId();
        }
    }

    @RequestMapping(value = "/ong/changepassword", method = RequestMethod.POST)
    public String ongchangepassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ongid") == null)
            return "redirect:/login";
        Ong ong = ongService.getOngById(Integer.parseInt(session.getAttribute("ongid").toString()));
        String password = request.getParameter("password");
        String verifypassword = request.getParameter("verifypassword");
        String newpassword = request.getParameter("newpassword");
        if(password.equals(verifypassword) == false)
            return "redirect:/ong/" + ong.getId();
        else {
            ong.setPassword(newpassword);
            ongService.saveOng(ong);
            return "redirect:/ong/" + ong.getId();
        }
    }
    @RequestMapping(value = "/forgotpassword",method = RequestMethod.GET)
    public String forgotPassword() {
        return "forgotpassword";
    }

    @RequestMapping(value = "/forgotpassword",method = RequestMethod.POST)
    public String sendEmail(HttpServletRequest request) throws MessagingException {
        try {
            String email = request.getParameter("email");
            String password = personService.getPersonByEmail(email).getPassword();
            String user = personService.getPersonByEmail(email).getFullName();
            if (password == null) {
                user = ongService.getOngByEmail(email).getUsername();
                password = ongService.getOngByEmail(email).getPassword();
            }
            SmtpMailSender.sendMail(email, "Your password", "Your current password is " + password + " and username is " + user);

            return "redirect:/login";

        }
        catch(Exception e) {
            return "redirect:/login";
        }
    }
}
