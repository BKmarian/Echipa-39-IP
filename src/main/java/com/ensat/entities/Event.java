package com.ensat.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "ong_id")
    protected Ong ong;

    private String name;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date date;
    private String location;
    private String description;
    private Boolean approved;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public Ong getOng() {
        return ong;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOng(Ong ong) {
        this.ong = ong;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String displayFormattedDate() {
        Date unformattedDate = this.date;
        return dateFormatter.format(unformattedDate);
    }

    @Override
    public String toString() {
        return " " + name + "\n locatie=" + location + "\n data=" + date + "\n descriere=" + description + "\n Ong = " + ong.getFullName();
    }

}
