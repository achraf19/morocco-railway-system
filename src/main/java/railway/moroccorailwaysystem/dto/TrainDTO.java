package railway.moroccorailwaysystem.dto;

import railway.moroccorailwaysystem.model.TrainType;
import java.time.LocalDate;

public record TrainDTO(
        Integer trainNumber,
        TrainType trainType,
        LocalDate commissioningDate,
        int totalSeats
) {
}
