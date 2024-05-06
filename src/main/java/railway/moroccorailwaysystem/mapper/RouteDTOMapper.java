package railway.moroccorailwaysystem.mapper;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.RouteDTO;
import railway.moroccorailwaysystem.model.Route;
import java.util.function.Function;

@Service
public class RouteDTOMapper implements Function<Route, RouteDTO> {

    @Override
    public RouteDTO apply(Route route) {
        return new RouteDTO(
                route.getDepartureTime(),
                route.getArrivalTime(),
                route.getPrice(),
                route.getStops(),
                route.getChanges(),
                route.getTrainStations(),
                route.getArrivalTimes(),
                route.getTrain().getTrainNumber(),
                route.getRailwayLine().getRailwayLineNumber()
        );
    }
}
