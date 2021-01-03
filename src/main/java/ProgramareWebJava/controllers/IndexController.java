package ProgramareWebJava.controllers;

import ProgramareWebJava.entities.Event;
import ProgramareWebJava.entities.Person;
import ProgramareWebJava.entities.User;
import ProgramareWebJava.services.EventService;
import ProgramareWebJava.services.OngService;
import ProgramareWebJava.services.PersonService;
import ProgramareWebJava.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private PersonService personService;

    @Autowired
    private OngService ongService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/login", "/"})
    @ResponseBody
    @ApiOperation(
            value = "Get last five elements",
            response = List.class)
    public ResponseEntity<List<Event>> getEventImages() {
        List<Event> lista = eventService.findsLastFIVEvents();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    @ApiOperation(
            value = "Logout and Destroy session parameter",
            response = String.class)
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = session.getAttribute("username").toString();
        session.invalidate();
        return new ResponseEntity<>("Logout user " + name, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    @ResponseBody
    @ApiOperation(
            value = "Login user with username and password",
            response = String.class)
    public ResponseEntity<String> login(HttpServletRequest request, @ApiParam("The username and password of the user") @RequestParam Map<String, String> parameters) {
        String username = parameters.get("username");
        String password = parameters.get("password");
        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                request.getSession().setAttribute("username", username);
                if (user.getIsadmin())
                    return new ResponseEntity<>("Admin connected with name" + user.getUsername(), HttpStatus.OK);
                if (user instanceof Person)
                    return new ResponseEntity<>("Person connected with name " + user.getUsername(), HttpStatus.OK);
                else
                    return new ResponseEntity<>("Ong connected with name " + user.getUsername(), HttpStatus.OK);

            }
            return new ResponseEntity<>("User does not exist", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while logging " + e.getMessage(), HttpStatus.OK);
        }
    }
}
