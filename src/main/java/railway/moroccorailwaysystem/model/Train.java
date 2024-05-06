package railway.moroccorailwaysystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private Integer trainNumber;
    @Enumerated(EnumType.STRING)
    private TrainType trainType;
    @Column(nullable = false)
    private LocalDate commissioningDate;
    @Column(nullable = false)
    private int totalSeats;
    private boolean areSeatsLimited;

    public Train(
            Integer trainNbr,
            TrainType trainType,
            LocalDate commissioningDate,
            int totalSeats
    ) {
        this.trainNumber = trainNbr;
        this.trainType = trainType;
        this.commissioningDate = commissioningDate;
        this.totalSeats = totalSeats;
    }
}
