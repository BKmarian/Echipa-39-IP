package com.ensat.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "ong")
public class Ong extends User {

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
        return super.toString() + " numar telefon= " + phone;
    }

    public static class Builder {

        /// instance fields
        private String fullName;
        private String phone;
        private Boolean approved;
        private String contact;
        private Integer id;
        private String username;
        private String password;
        private String email;
        private Boolean isadmin;

        public Ong build() {
            Ong ong = new Ong();
            ong.fullName = this.fullName;
            ong.phone = this.phone;
            ong.approved = this.approved;
            ong.contact = this.contact;
            ong.id = this.id;
            ong.username = this.username;
            ong.password = this.password;
            ong.email = this.email;
            ong.isadmin = this.isadmin;
            return ong;
        }

        public Ong.Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Ong.Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Ong.Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Ong.Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Ong.Builder withFullName(String email) {
            this.fullName = fullName;
            return this;
        }

        public Ong.Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Ong.Builder withIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
            return this;
        }

        public Ong.Builder withApproved(Boolean approved) {
            this.approved = approved;
            return this;
        }

        public Ong.Builder withContact(String contact) {
            this.contact = contact;
            return this;
        }

    }
}
