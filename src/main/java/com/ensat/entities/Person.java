package com.ensat.entities;

import javax.persistence.*;

/**
 *
 */
@Entity
@DiscriminatorValue(value = "person")
public class Person extends User{

    @Column(name = "full_name")
    private String fullName;


    private String phone;
    private String picture;
    private Integer age;
    private String job;


    public String getPicture() {
        return picture;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", fullName='" + fullName + '\'' +
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

    public String getFullName() {
        return fullName;
    }

}
