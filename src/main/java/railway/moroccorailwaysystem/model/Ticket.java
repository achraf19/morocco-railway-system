package railway.moroccorailwaysystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private Integer ticketNumber;
    private TicketType ticketType;
    private String from;
    private String to;
    private LocalDate departureDate;
    private Time departureTime;
    private Time arrivalTime;
    private BigDecimal price;
    private int stops;
    private int changes;
    private TicketStatus ticketStatus;
    @ManyToOne
    @JoinColumn(
            name = "train_id",
            referencedColumnName = "ID",
            nullable = false
    )
    private Train train;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "ID",
            nullable = false)
    private User user;

    public Ticket(
            Integer ticketNumber,
            TicketType ticketType,
            String from,
            String to,
            LocalDate departureDate,
            Time departureTime,
            Time arrivalTime,
            int stops,
            int changes,
            BigDecimal price,
            Train train,
            User user
    ) {
        this.ticketNumber = ticketNumber;
        this.ticketType = ticketType;
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.stops = stops;
        this.changes = changes;
        this.price = price;
        this.train = train;
        this.user = user;
    }
}


