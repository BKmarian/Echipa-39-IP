package com.ensat.configuration;


import com.ensat.entities.Event;
import com.ensat.entities.Person;
import com.ensat.services.EventService;
import com.ensat.services.PersonService;
import com.ensat.services.User2eventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class SchedulingTask {

    @Autowired
    private EventService eventService;

    @Autowired
    private PersonService personService;

    @Autowired
    private User2eventService user2eventService;

    private static final Logger log = LoggerFactory.getLogger(SchedulingTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final String fromEmail = "cosminmanolescudans@gmail.com";

    //SCHEDULED ONCE EVERY DAY
    //Notify users with their given events
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void reportCurrentTime() throws MessagingException {
        ArrayList<Person> personList = (ArrayList<Person>) personService.listAllPersons();
        for (Person person : personList) {
            ArrayList<Event> events = (ArrayList<Event>) user2eventService.getEventsbyPerson(person);
            for (Event event : events) {
                String text = "Zilele acestea va avea loc evenimentul: " + event.toString();
                String subject = "Event";
                int days = daysBetween(new Date(), event.getDate());
                System.out.println("number of days until next event " + days);
                //TO ADD LATER
                if (days < 3 && days >= 0)
                    SmtpMailSender.sendMail(person.getEmail(), subject, text);
            }
        }
        log.info("Sent emails for {}", dateFormat.format(new Date()));
    }

    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}

