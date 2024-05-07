package railway.moroccorailwaysystem.service;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.RouteDTO;
import railway.moroccorailwaysystem.mapper.RouteDTOMapper;
import railway.moroccorailwaysystem.model.RailwayLine;
import railway.moroccorailwaysystem.model.Route;
import railway.moroccorailwaysystem.model.Train;
import railway.moroccorailwaysystem.repo.RouteRepository;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteDTOMapper routeMapper;
    private final TrainService trainService;
    private final RailwayLineService railwayLineService;

    public RouteService(RouteRepository routeRepository, RouteDTOMapper routeMapper, TrainService trainService, RailwayLineService railwayLineService) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.trainService = trainService;
        this.railwayLineService = railwayLineService;
    }

    public void SaveNewRoute(RouteDTO routeDTO) {
        Train train = trainService.getTrainByTrainNbr(routeDTO.trainNumber());
        RailwayLine railwayLine = railwayLineService.getRailwayLineByRef(routeDTO.trackNumber());

        Route newRoute = new Route(
                routeDTO.from(),
                routeDTO.to(),
                routeDTO.departureTime(),
                routeDTO.arrivalTime(),
                routeDTO.price(),
                routeDTO.stops(),
                routeDTO.changes(),
                routeDTO.daysOfWeek(),
                routeDTO.trainStations(),
                routeDTO.arrivalTimes(),
                train,
                railwayLine
        );

        routeRepository.save(newRoute);
    }
}
