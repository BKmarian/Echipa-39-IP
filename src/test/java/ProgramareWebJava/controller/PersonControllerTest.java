package ProgramareWebJava.controller;

import ProgramareWebJava.SpringBootWebApplication;
import ProgramareWebJava.controllers.*;
import ProgramareWebJava.entities.Event;
import ProgramareWebJava.entities.Ong;
import ProgramareWebJava.entities.Person;
import ProgramareWebJava.entities.User;
import ProgramareWebJava.services.EventService;
import ProgramareWebJava.services.UserToEventService;
import ProgramareWebJava.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonControllerTest {

    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private static final HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    private static final CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    @Autowired
    private NewUsersController newUsersController;

    @Autowired
    private PersonController personController;

    @Autowired
    private IndexController indexController;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserToEventService userToEventService;

    @Autowired
    private OngController ongController;

    @Autowired
    private ChangePassController changePassController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController, changePassController, newUsersController, indexController, userToEventService, ongController)
                .build();

    }

    @Test
    public void a_login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void b_testPersonService() throws Exception {
        User person = Person.builder()
                .age(24)
                .job("software engineer")
                .fullName("Person 1")
                .phone("0722647764")
                .email("sichitium@yahoo.com")
                .username("username2")
                .password("password2")
                .isadmin(false)
                .build();
        String requestJson = getJsonFromEntity(person);


        mockMvc.perform(post("/login")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .param("username", "admin")
                .param("password", "admin"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(put("/createperson")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("username2 saved")));


        MvcResult result = mockMvc.perform(get("/person/profile")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username2")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Person personRes = mapper.readValue(result.getResponse().getContentAsString(), Person.class);
        person.setId(person.getId());
        assertEquals(personRes, person);
    }

    @Test
    public void c_updatePerson() throws Exception {
        Person person = (Person) userService.getUserByEmail("sichitium@yahoo.com");
        person.setFullName("Person Updated");
        person.setUsername("username3");
        String requestJson = getJsonFromEntity(person);

        mockMvc.perform(post("/updatePerson")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Person with id: username3 updated ")));

        assertNotNull(userService.getUserByUsername("username3"));
        assertEquals(((Person) userService.getUserByEmail("sichitium@yahoo.com")).getFullName(), "Person Updated");
    }

    @Test
    public void d_postLogin() throws Exception {

        mockMvc.perform(post("/login")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .param("username", "username3")
                .param("password", "password2"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Person connected with name username3")));
    }

    @Test
    public void e_createAndDeleteEvent() throws Exception {

        Ong ong = Ong.builder()
                .approved(false)
                .contact("0722647764")
                .fiscalcode("12345677")
                .fullName("Ong 1")
                .phone("0722647764")
                .email("sichitium@yahoo.com")
                .username("username2")
                .password("password2")
                .isadmin(false)
                .build();
        String requestJson = getJsonFromEntity(ong);

        mockMvc.perform(put("/createong")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("username2 saved")));

        mockMvc.perform(post("/ong/event")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username2")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("date", "2020-08-09")
                .param("description", "Va fi misto")
                .param("name", "Ong caini")
                .param("address", "Strada Academiei 11")
                .param("postal_code", "210112")
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        Integer eventId = eventService.getEventsByOng((Ong) userService.getUserByUsername("username2")).get(0).getId();
        MvcResult eventResult = mockMvc.perform(put("/person/joinevent/{eventid}", eventId)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        Event eventInDb = eventService.getEventsByOng((Ong) userService.getUserByUsername("username2")).get(0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Event responseEvent = mapper.readValue(eventResult.getResponse().getContentAsString(), Event.class);
        responseEvent.setDate(eventInDb.getDate());
        assertEquals(eventInDb, responseEvent);

        mockMvc.perform(post("/ong/event")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username2")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("date", "2021-08-09")
                .param("description", "Va fi tare")
                .param("name", "Ong pisici")
                .param("address", "Strada Academiei 12")
                .param("postal_code", "210113")
                .secure(true))
                .andExpect(status().isOk());

        eventResult = mockMvc.perform(get("/person/allevents")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        List<Event> events = mapper.readValue(eventResult.getResponse().getContentAsString(), List.class);
        assertEquals(events.size(), 1);
        List<Event> returnEvents = eventService.getEventsByOng((Ong) userService.getUserByUsername("username2"));
        assertEquals(returnEvents.size(), 2);
//        assertEquals(events.get(0).get("description"),"Va fi tare");
//        assertEquals(events.get(0).get("ong"), userService.getUserByUsername("username2"));

        mockMvc.perform(delete("/person/event/delete/{eventid}", eventInDb.getId())
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Deleted user username3")));

        assertEquals(userToEventService.getEventsbyPerson((Person) userService.getUserByUsername("username3")).size(), 0);

        eventResult = mockMvc.perform(get("/person/allevents")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        List<Event> resultEvents = mapper.readValue(eventResult.getResponse().getContentAsString(), List.class);
        assertEquals(resultEvents.size(), 0);

        mockMvc.perform(get("/logout")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Logout user username3")));
    }

    @Test
    public void g_changePassword() throws Exception {
        mockMvc.perform(post("/changepassword/{username}", "username3")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("password", "password2")
                .param("verifypassword", "password2")
                .param("newpassword", "password3")
                .secure(true))
                .andExpect(status().isOk());

        assertEquals("password3", userService.getUserByUsername("username3").getPassword());
    }

    @Test
    public void z_clean() {
        eventService.deleteAll();
        userService.deleteAll();
    }

    private <T> String getJsonFromEntity(T ong) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(ong);
    }
}