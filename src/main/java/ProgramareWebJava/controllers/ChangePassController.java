package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ChangePassController {

    @Autowired
    private PersonService personService;

    @Autowired
    private OngService ongService;

    @Autowired
    private UserService userService;

    final String fromEmail = "cosminmanolescudans@gmail.com";

    @PostMapping(value = "/person/changepassword")
    @ResponseBody
    public Person personChangePassword(@PathVariable("userid") String userid, @RequestParam Map<String, String> parameters) {
        Person person = (Person) userService.getUserById(Integer.parseInt(userid));
        String password = parameters.get("password");
        String verifyPassword = parameters.get("verifypassword");
        String newPassword = parameters.get("newpassword");
        if (password.equals(verifyPassword)) {
            person.setPassword(newPassword);
            userService.saveUser(person);
        }
        return person;
    }

    @PostMapping(value = "/ong/changepassword")
    @ResponseBody
    public Ong ongChangePassword(@PathVariable("ongid") String ongid, @RequestParam Map<String, String> parameters) {
        Ong ong = (Ong) userService.getUserById(Integer.parseInt(ongid));
        String password = parameters.get("password");
        String verifyPassword = parameters.get("verifypassword");
        String newPassword = parameters.get("newpassword");
        if (password.equals(verifyPassword)) {
            ong.setPassword(newPassword);
            userService.saveUser(ong);
        }
        return ong;
    }

//    @PostMapping(value = "/forgotpassword")
//    @ResponseBody
//    public String sendEmail(@RequestParam Map<String, String> parameters) {
//        String email = parameters.get("email");
//        try {
//            String password = userService.getUserByEmail(email).getPassword();
//            String user = userService.getUserByEmail(email).getUsername();
//            if (password == null) {
//                user = userService.getUserByEmail(email).getUsername();
//                password = userService.getUserByEmail(email).getPassword();
//            }
//            //SmtpMailSender.sendMail(email, "Your password", "Your current password is " + password + " and username is " + user);
//
//            return "Email was sent to" + email;
//
//        } catch (Exception e) {
//            return "Error sending email to" + email;
//        }
//    }
}
