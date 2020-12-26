package ProgramareWebJava.controller;

import ProgramareWebJava.SpringBootWebApplication;
import ProgramareWebJava.advice.ProjectExceptionHandler;
import ProgramareWebJava.controllers.*;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionHandlerTest {

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

    @Autowired
    private ProjectExceptionHandler projectExceptionHandler;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectExceptionHandler,personController, newUsersController, ongController, adminController, indexController)
                .build();

    }

    @Test
    public void a_not_found() throws Exception {
        mockMvc.perform(post("/loginasdasd")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .secure(true)
                .param("username", "admin")
                .param("password", "admin"))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void b_invalid_person() throws Exception {
        User person = Person.builder()
                .age(105)
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
                .andExpect(status().isBadRequest());

    }

    private <T> String getJsonFromEntity(T ong) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(ong);
    }
}