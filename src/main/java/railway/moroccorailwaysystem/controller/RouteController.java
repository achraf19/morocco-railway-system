package railway.moroccorailwaysystem.controller;

import org.springframework.web.bind.annotation.*;
import railway.moroccorailwaysystem.service.RouteService;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

}
