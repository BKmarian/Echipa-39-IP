package ProgramareWebJava.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "person")
public class Person extends User {

    @Column(name = "full_name")
    @NotNull
    private String fullName;

    @NotNull
    @Length(min = 4, max = 25)
    @Pattern(regexp = "\\d+")
    private String phone;

    @URL
    private String picture;

    @Range(min = 18, max = 99)
    private Integer age;

    private String job;

    @Builder()
    public Person(String username, String password, String email, Boolean isadmin, String fullName, String phone, String picture, Integer age, String job) {
        super(username, password, email, isadmin);
        this.fullName = fullName;
        this.phone = phone;
        this.picture = picture;
        this.age = age;
        this.job = job;
    }
    public Person() {

    }
}