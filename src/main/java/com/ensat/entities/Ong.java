package com.ensat.entities;

import javax.persistence.*;

/**
 *

 */
@Entity
@DiscriminatorValue(value = "ong")
public class Ong extends User{

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

    public String getFullName() {
        return fullName;
    }


    @Override
    public String toString() {
        return super.toString()  + " numar telefon= " + phone;
    }

}
