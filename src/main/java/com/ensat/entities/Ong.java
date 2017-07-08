package com.ensat.entities;

import javax.persistence.*;

/**
 *

 */
@Entity
@Table(name = "Ong")
public class Ong {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private String email;

    @Column(name = "full_name")
    private String fullName;
    private String phone;
    private Boolean approved;
    private String contact;

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public String getContact() {
        return contact;

    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private String fiscalcode;

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

    @Override
    public String toString() {
        return " username=" + username + " email" + email + " numar telefon= " + phone;
    }

}
