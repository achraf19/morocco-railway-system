package railway.moroccorailwaysystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.*;
import railway.moroccorailwaysystem.dto.payload.BookingTicketRequest;
import railway.moroccorailwaysystem.exception.DuplicatedResourceException;
import railway.moroccorailwaysystem.exception.ResourceNotFoundException;
import railway.moroccorailwaysystem.mapper.UserDTOMapper;
import railway.moroccorailwaysystem.mapper.RouteDTOMapper;
import railway.moroccorailwaysystem.model.Route;
import railway.moroccorailwaysystem.model.UserType;
import railway.moroccorailwaysystem.repo.UserRepository;
import railway.moroccorailwaysystem.repo.RouteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final RouteRepository routeRepository;
    private final RouteDTOMapper routeMapper;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserService.class);

    public UserService(
            UserRepository userRepository,
            UserDTOMapper userDTOMapper,
            RouteRepository routeRepository,
            RouteDTOMapper routeMapper
    ) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    public void checkIfUserAlreadyExists(String email) {
        boolean userExists = userRepository.existsByEmail(email);
        if(userExists) {
            throw new DuplicatedResourceException(
                    "user with email %s already exists!"
                            .formatted(email)
            );
        }
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getCustomers() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getUserType().equals(UserType.CUSTOMER))
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "user with email %s not found".formatted(email)
                        )
                );
    }

    public void checkIfUserExistsById(Integer id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                  "user with ID %s not found".formatted(id)
            );
        }
    }

    public void deleteUserById(Integer id) {
        checkIfUserExistsById(id);
        userRepository.deleteById(id);
    }

    public List<RouteDTO> getTrainTimeTables(String from, String to, LocalDate departureDate) {
        return routeRepository
                .findByDepartureStationAndArrivalStation(
                        from, to
                )
                .stream()
                .filter(r ->
                        isTrainCommissioningDay(
                                r,
                                departureDate != null ? departureDate : LocalDate.now()
                        )
                )
                .map(routeMapper)
                .collect(Collectors.toList());
    }

    private boolean isTrainCommissioningDay(Route route, LocalDate departureDate) {
        String dayOfWeek = departureDate.getDayOfWeek().toString();
        return route.getDaysOfWeek()
                .stream().anyMatch(d -> d.equalsIgnoreCase(dayOfWeek));
    }
}
