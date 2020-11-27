package com.ensat.entities;

import javax.persistence.*;

/**
 *
 */
@Entity
@DiscriminatorValue(value = "person")
public class Person extends User {

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

    public static class Builder {

        /// instance fields
        private String fullName;
        private String phone;
        private String picture;
        private Integer age;
        private String job;
        private Integer id;
        private String username;
        private String password;
        private String email;
        private Boolean isadmin;

        public Person build() {
            Person person = new Person();
            person.fullName = this.fullName;
            person.phone = this.phone;
            person.picture = this.picture;
            person.age = this.age;
            person.job = this.job;
            person.id = this.id;
            person.username = this.username;
            person.password = this.password;
            person.email = this.email;
            person.isadmin = this.isadmin;
            return person;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
            return this;
        }

        public Builder withPicture(String picture) {
            this.picture = picture;
            return this;
        }

        public Builder withAge(String age) {
            this.age = Integer.valueOf(age);
            return this;
        }

        public Builder withJob(String job) {
            this.job = job;
            return this;
        }

    }
}
