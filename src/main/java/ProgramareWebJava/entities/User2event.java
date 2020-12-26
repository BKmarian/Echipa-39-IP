package ProgramareWebJava.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "User2event")
@NoArgsConstructor
public class User2event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "event_id")
    @NotNull
    protected Event event;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @NotNull
    protected Person person;

    public User2event(Person person, Event event) {
        this.event = event;
        this.person = person;
    }
}