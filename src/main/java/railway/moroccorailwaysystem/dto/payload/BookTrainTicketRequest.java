package railway.moroccorailwaysystem.dto.payload;

import railway.moroccorailwaysystem.model.TicketType;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

public record BookTrainTicketRequest(
        TicketType ticketType,
        String from,
        String to,
        LocalDate departureDate,
        Time departureTime,
        Time arrivalTime,
        int trackNumber,
        int stops,
        int changes,
        BigDecimal price,
        String email
) {
}
