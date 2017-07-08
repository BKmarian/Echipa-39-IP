package com.ensat.entities;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "isadmin")
    private boolean isadmin;

    private String phone;
    private Boolean approved;
    private String picture;
    private Integer age;
    private String job;

    public boolean getIsAdmin() { return isadmin;}

    public String getPicture() {
        return picture;
    }

    public String getJob() {
        return job;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;

    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {

        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return " username=" + password + " email" + email;
    }
}
