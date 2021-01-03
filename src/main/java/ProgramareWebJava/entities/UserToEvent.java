package ProgramareWebJava.entities;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "User2event")
@NoArgsConstructor
@ApiModel(value="UserEvent", description="Mergind class for Many to Many relation between event and user")
public class UserToEvent {

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

    public UserToEvent(Person person, Event event) {
        this.event = event;
        this.person = person;
    }
}