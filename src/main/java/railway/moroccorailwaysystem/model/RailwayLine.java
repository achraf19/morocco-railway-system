package railway.moroccorailwaysystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "railway_lines")
public class RailwayLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer railwayLineNumber;
    @Column(nullable = false)
    private LocalDate established;

    public RailwayLine(
            Integer railwayLineNbr,
            LocalDate established
    ) {
        this.railwayLineNumber = railwayLineNbr;
        this.established = established;
    }
}
