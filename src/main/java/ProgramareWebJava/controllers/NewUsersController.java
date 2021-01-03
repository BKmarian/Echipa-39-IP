package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.*;
import ProgramareWebJava.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class NewUsersController {

    @Autowired
    private PersonService personService;

    @Autowired
    private OngService ongService;

    @Autowired
    private UserService userService;

    @PutMapping(value = "/createperson")
    @ResponseBody
    @ApiOperation(
            value = "Create Person user",
            response = String.class)
    public ResponseEntity<String> createPerson(@ApiParam("The person parameters") @Valid @RequestBody Person person) {
        String username = person.getUsername();
        try {
            if (userService.getUserByUsername(username) != null)
                return new ResponseEntity<>("Username " + username + " already exists", HttpStatus.IM_USED);
            userService.saveUser(person);
            return new ResponseEntity<>(username + " saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Problem: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/createong")
    @ResponseBody
    @ApiOperation(
            value = "Create Ong user",
            response = String.class)
    public ResponseEntity<String> createOng(@ApiParam("The ong parameters") @Valid @RequestBody Ong ong) {
        String username = ong.getUsername();
        try {
            if (userService.getUserByUsername(username) != null)
                return new ResponseEntity<>("Username " + username + " already exists", HttpStatus.IM_USED);
            userService.saveUser(ong);
            return new ResponseEntity<>(username + " saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Problem: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
