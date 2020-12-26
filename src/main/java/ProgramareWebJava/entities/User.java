package ProgramareWebJava.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

import org.hibernate.validator.constraints.*;
import lombok.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = false,exclude = {"id"})
@Table(name = "user")
@Inheritance
@NoArgsConstructor
@DiscriminatorColumn(name = "user_type")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @NotNull
    protected String username;

    @NotNull
    protected String password;

    @Email
    protected String email;

    protected Boolean isadmin;

    public User(String username, String password, String email, Boolean isadmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isadmin = isadmin;

    }

}