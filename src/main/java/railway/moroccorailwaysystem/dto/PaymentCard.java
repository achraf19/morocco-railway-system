package railway.moroccorailwaysystem.dto;

import java.time.LocalDate;

public record PaymentCard(
        String cardHolderName,
        int cardNumber,
        LocalDate expires,
        int CVV
) {
}
