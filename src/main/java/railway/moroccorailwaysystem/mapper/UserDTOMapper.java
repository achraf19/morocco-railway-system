package railway.moroccorailwaysystem.mapper;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.UserDTO;
import railway.moroccorailwaysystem.model.User;
import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User u) {
        return new UserDTO(
                u.getFirstName(),
                u.getLastName(),
                u.getAge(),
                u.getEmail()
        );
    }
}
