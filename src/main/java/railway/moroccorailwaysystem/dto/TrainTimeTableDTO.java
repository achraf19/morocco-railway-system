package railway.moroccorailwaysystem.dto;

import java.time.LocalDate;
import java.util.List;

public record TrainTimeTableDTO(
        String from,
        String to,
        LocalDate departureDate,
        List<RouteDTO> routes
) {
}
