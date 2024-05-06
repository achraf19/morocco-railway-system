package railway.moroccorailwaysystem.dto;

import java.time.LocalDate;

public record RailwayLineDTO(
        Integer railwayLineRef,
        LocalDate established
) {
}
