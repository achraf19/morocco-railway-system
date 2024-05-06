package railway.moroccorailwaysystem.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import railway.moroccorailwaysystem.dto.*;
import railway.moroccorailwaysystem.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/customer")
    public Principal getLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/check-train-time-tables")
    public TrainTimeTableDTO checkTrainTimeTables(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) LocalDate departureDate
    ) {
        List<RouteDTO> routes = userService.getTrainTimeTables(from, to, departureDate);
        return new TrainTimeTableDTO(
                from,
                to,
                departureDate != null ? departureDate : LocalDate.now(),
                routes
        );
    }
}