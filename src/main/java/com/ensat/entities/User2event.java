package com.ensat.entities;

import javax.persistence.*;

/**
 * Created by oem on 3/21/17.
 */
@Entity
@Table(name = "User2event")
public class User2event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "event_id")
    protected Event event;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    protected Person person;

    public Person getPerson() {
        return person;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User2event(Person person , Event event) {
        this.event = event;
        this.person = person;
    }
    public User2event() {

    }
    public String toString() {
        return "Event=" + event + " Person=" + person;
    }
}