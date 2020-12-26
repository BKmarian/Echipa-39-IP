package ProgramareWebJava.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "ong_id")
    protected ProgramareWebJava.entities.Ong ong;

    @NotNull
    private String name;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date date;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_id")
    private Location location;

    private String description;
    private Boolean approved;

    @Builder()
    public Event(Ong ong, String name, Date date, Location location, String description, boolean approved) {
        this.ong = ong;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.approved = approved;
    }

    public Event() {

    }

}
