package ProgramareWebJava.entities;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@DiscriminatorValue(value = "ong")
public class Ong extends User {

    @Column(name = "full_name")
    @NotNull
    private String fullName;

    @NotNull
    @Length(min = 4, max = 25)
    @Pattern(regexp = "\\d+")
    private String phone;

    @Column(columnDefinition = "boolean default false")
    private Boolean approved;

    @NotNull
    @Length(min = 4, max = 25)
    @Pattern(regexp = "\\d+")
    private String fiscalcode;

    private String contact;

    @Builder()
    public Ong(String username, String password, String email, Boolean isadmin, String fullName, String phone, String fiscalcode, String contact, Boolean approved) {
        super(username, password, email, isadmin);
        this.fullName = fullName;
        this.phone = phone;
        this.fiscalcode = fiscalcode;
        this.contact = contact;
        this.approved = approved;
    }

    public Ong() {

    }
}
