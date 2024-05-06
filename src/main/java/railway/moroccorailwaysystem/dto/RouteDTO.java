package railway.moroccorailwaysystem.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

public record RouteDTO(
    Time departureTime,
    Time arrivalTime,
    BigDecimal price,
    int stops,
    int changes,
    List<String> trainStations,
    List<String> arrivalTimes,
    Integer trainNumber,
    Integer trackNumber
) {
}
