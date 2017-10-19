package com.ensat.entities;

import javax.persistence.*;

/**
 *
 */
@Entity
@DiscriminatorValue(value = "person")
public class Person extends User{

    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "isadmin")
    private Boolean isadmin;

    private String phone;
    private String picture;
    private Integer age;
    private String job;

    public Boolean getIsAdmin() { return isadmin;}

    public String getPicture() {
        return picture;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", isadmin=" + isadmin +
                ", phone='" + phone + '\'' +
                ", picture='" + picture + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

}
