package ProgramareWebJava.controller;

import ProgramareWebJava.SpringBootWebApplication;
import ProgramareWebJava.controllers.*;
import ProgramareWebJava.entities.Ong;
import ProgramareWebJava.entities.Person;
import ProgramareWebJava.entities.User;
import ProgramareWebJava.services.EventService;
import ProgramareWebJava.services.OngService;
import ProgramareWebJava.services.PersonService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminControllerTest {

    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private static final HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    private static final CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    @Autowired
    private OngController ongController;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PersonController personController;

    @Autowired
    private NewUsersController newUsersController;

    @Autowired
    private PersonService personService;

    @Autowired
    private OngService ongService;

    @Autowired
    private AdminController adminController;

    @Autowired
    private IndexController indexController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController, newUsersController, ongController, adminController, indexController)
                .build();

    }

    @Test
    public void a_deletePerson() throws Exception {
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

        Person pers = StreamSupport
                .stream(personService.listAllPersons().spliterator(), false)
                .collect(Collectors.toList()).get(0);

        mockMvc.perform(delete("/admin/deletePerson/{personId}", pers.getId())
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/admin/persons")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<Person> personRes = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(0, personRes.size());
    }

    @Test
    public void b_deleteEventAndOng() throws Exception {

        User ong = Ong.builder()
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

        Ong ongFromDb = StreamSupport
                .stream(ongService.findOngsToAccept().spliterator(), false)
                .collect(Collectors.toList()).get(0);

        mockMvc.perform(post("/admin/acceptong/{ongId}", ongFromDb.getId())
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Ong with id " + ongFromDb.getId() + " was accepted")));

        assertEquals(StreamSupport.stream(ongService.findOngsToAccept().spliterator(), false).count(),0);
        assertEquals(StreamSupport.stream(ongService.findOngsAccepted().spliterator(), false).count(),1);

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

        mockMvc.perform(delete("/admin/deleteOng/{ongId}", ongFromDb.getId())
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());

        int size = (int) StreamSupport.stream(eventService.listAllEvents().spliterator(), false).count();

        assertEquals(0, size);

        MvcResult result = mockMvc.perform(get("/admin/ongs")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<Ong> personRes = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(0, personRes.size());
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
