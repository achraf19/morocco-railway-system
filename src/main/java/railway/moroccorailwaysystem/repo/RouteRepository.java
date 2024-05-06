package railway.moroccorailwaysystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import railway.moroccorailwaysystem.model.Route;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query("SELECT r FROM Route r WHERE r.from = ?1 AND r.to = ?2")
    List<Route> findByDepartureStationAndArrivalStation(String from, String to);
}
