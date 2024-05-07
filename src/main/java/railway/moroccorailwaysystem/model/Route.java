package railway.moroccorailwaysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    @Column(name = "departure_station", nullable = false)
    private String from;
    @Column(name = "arrival_station", nullable = false)
    private String to;
    private LocalDate departureDate;
    private Time departureTime;
    private Time arrivalTime;
    @Column(columnDefinition = "JSON")
    @Type(JsonType.class)
    private List<String> trainStations;
    @Column(columnDefinition = "JSON")
    @Type(JsonType.class)
    private List<String> arrivalTimes;
    private BigDecimal price;
    int stops;
    int changes;
    @JsonIgnore
    @Column(columnDefinition = "JSON")
    @Type(JsonType.class)
    List<String> daysOfWeek;
    @OneToOne
    @JoinColumn(name = "train_id",
            referencedColumnName = "ID",
            nullable = false
    )
    private Train train;
    @ManyToOne
    @JoinColumn(name = "railway_line_id",
            referencedColumnName = "ID",
            nullable = false
    )
    private RailwayLine railwayLine;

    public Route(
            String from,
            String to,
            LocalDate departureDate,
            Time departureTime,
            Time arrivalTime,
            List<String> trainStations,
            List<String> arrivalTimes,
            BigDecimal price,
            int stops,
            int changes,
            List<String> daysOfWeek,
            Train train,
            RailwayLine railwayLine
    ) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.trainStations = trainStations;
        this.arrivalTimes = arrivalTimes;
        this.price = price;
        this.stops = stops;
        this.changes = changes;
        this.daysOfWeek = daysOfWeek;
        this.train = train;
        this.railwayLine = railwayLine;
    }
}

