package railway.moroccorailwaysystem.mapper;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.TrainDTO;
import railway.moroccorailwaysystem.model.Train;
import java.util.function.Function;

@Service
public class TrainDTOMapper implements Function<Train, TrainDTO> {
    @Override
    public TrainDTO apply(Train train) {
        return new TrainDTO(
                train.getTrainNumber(),
                train.getTrainType(),
                train.getCommissioningDate(),
                train.getTotalSeats()
        );
    }
}
