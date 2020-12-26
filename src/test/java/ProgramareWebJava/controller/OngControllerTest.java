package ProgramareWebJava.controller;

import ProgramareWebJava.SpringBootWebApplication;
import ProgramareWebJava.controllers.IndexController;
import ProgramareWebJava.controllers.NewUsersController;
import ProgramareWebJava.controllers.OngController;
import ProgramareWebJava.entities.Event;
import ProgramareWebJava.entities.Ong;
import ProgramareWebJava.entities.User;
import ProgramareWebJava.services.EventService;
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
public class OngControllerTest {

    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private static final HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    private static final CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    @Autowired
    private NewUsersController newUsersController;

    @Autowired
    private OngController ongController;

    @Autowired
    private IndexController indexController;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(ongController, newUsersController, indexController)
                .build();

    }

    @Test
    public void a_login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void b_testOngService() throws Exception {
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


        MvcResult loginResult = mockMvc.perform(post("/login")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .param("username", "admin")
                .param("password", "admin"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(put("/createong")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("username2 saved")));


    }

    @Test
    public void c_updateOng() throws Exception {
        Ong ong = (Ong) userService.getUserByEmail("sichitium@yahoo.com");
        ong.setFullName("Ong Updated");
        ong.setUsername("username3");
        String requestJson = getJsonFromEntity(ong);

        mockMvc.perform(post("/updateOng")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Ong with id: username3 updated ")));

        assertNotNull(userService.getUserByUsername("username3"));
        assertEquals(((Ong) userService.getUserByEmail("sichitium@yahoo.com")).getFullName(), "Ong Updated");
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
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Ong connected with name username3")));
    }

    @Test
    public void e_createAndDeleteEvent() throws Exception {
        MvcResult result = mockMvc.perform(post("/ong/event")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("date", "2020-08-09")
                .param("description", "Va fi misto")
                .param("name", "Ong caini")
                .param("address", "Strada Academiei 11")
                .param("postal_code", "210112")
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Event response = mapper.readValue(result.getResponse().getContentAsString(), Event.class);
        assertEquals(response.getName(), "Ong caini");
        assertEquals(response.getDescription(), "Va fi misto");

        result = mockMvc.perform(post("/ong/event")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("date", "2021-08-09")
                .param("description", "Va fi tare")
                .param("name", "Ong pisici")
                .param("address", "Strada Academiei 12")
                .param("postal_code", "210113")
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        response = mapper.readValue(result.getResponse().getContentAsString(), Event.class);
        assertEquals(response.getName(), "Ong pisici");
        assertEquals(response.getDescription(), "Va fi tare");

        Event event = eventService.getEventsByOng((Ong) userService.getUserByUsername("username3")).get(0);
        String jsonEvent = getJsonFromEntity(event);

        mockMvc.perform(post("/ong/event/update")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonEvent)
                .secure(true))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Event successfully updated")));

        result = mockMvc.perform(get("/ong/myEvents")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .sessionAttr("username", "username3")
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andReturn();

        List<Event> events = eventService.getEventsByOng((Ong) userService.getUserByUsername("username3"));
        String jsonEvents = getJsonFromEntity(events);
        String expectedJsonEvents = getJsonFromEntity(mapper.readValue(result.getResponse().getContentAsString(), List.class));
        assertEquals(expectedJsonEvents, jsonEvents);

        mockMvc.perform(delete("/event/delete/{eventid}", events.get(0).getId())
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Deleted event with id:" + events.get(0).getId())));
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