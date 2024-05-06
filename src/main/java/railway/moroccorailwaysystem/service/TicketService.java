package railway.moroccorailwaysystem.service;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.payload.BookingTicketRequest;

@Service
public class TicketService {

    public void buyTrainTicket(BookingTicketRequest bookingTicketRequest) {
        // CREATE A NEW TRAIN TICKET AND SAVE IT TO DB
        // PROCESS THE PAYMENT
        // IF PAYMENT IS SUCCESSFULLY DONE, THEN UPDATE TICKET_STATUS OF THE RECENT SAVED TICKET TO BOOKED
        // SEND TICKET VIA MAIL NOTIFICATION
    }
}
