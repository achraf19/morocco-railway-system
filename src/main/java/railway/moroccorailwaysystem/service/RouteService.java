package railway.moroccorailwaysystem.service;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.mapper.RouteDTOMapper;
import railway.moroccorailwaysystem.repo.RouteRepository;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteDTOMapper routeMapper;

    public RouteService(RouteRepository routeRepository, RouteDTOMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

}
