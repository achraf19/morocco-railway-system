package railway.moroccorailwaysystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import railway.moroccorailwaysystem.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
