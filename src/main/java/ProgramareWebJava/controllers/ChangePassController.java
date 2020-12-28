package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/changepassword/{username}")
    @ResponseBody
    @ApiOperation(
            value = "Get user by username",
            notes = "Change user password",
            response = User.class)
    public ResponseEntity<User> changePassword(@ApiParam("The username of the user") @PathVariable("username") String username,
                                               @ApiParam("Password parameters") @RequestParam Map<String, String> parameters) {
        User user = userService.getUserByUsername(username);
        String password = parameters.get("password");
        String verifyPassword = parameters.get("verifypassword");
        String newPassword = parameters.get("newpassword");
        if (password.equals(verifyPassword)) {
            user.setPassword(newPassword);
            userService.saveUser(user);
            //SmtpMailSender.sendMail(user.getEmail(), newPassword, "Your current password is " + password + " and username is " + user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
