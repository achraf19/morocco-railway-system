package railway.moroccorailwaysystem.dto.payload;

import railway.moroccorailwaysystem.dto.PaymentCard;
import railway.moroccorailwaysystem.dto.RouteDTO;

public record BookingTicketRequest(
        String email,
        RouteDTO route,
        PaymentCard paymentCard
) {
}
