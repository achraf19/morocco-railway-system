package railway.moroccorailwaysystem.service;

import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.TrainDTO;
import railway.moroccorailwaysystem.exception.DuplicatedResourceException;
import railway.moroccorailwaysystem.exception.ResourceNotFoundException;
import railway.moroccorailwaysystem.mapper.TrainDTOMapper;
import railway.moroccorailwaysystem.model.Train;
import railway.moroccorailwaysystem.repo.TrainRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainDTOMapper trainDTOMapper;

    public TrainService(
            TrainRepository trainRepository,
            TrainDTOMapper trainDTOMapper
    ) {
        this.trainRepository = trainRepository;
        this.trainDTOMapper = trainDTOMapper;
    }

    public void saveNewTrain(TrainDTO trainDTO) {
        checkIfTrainAlreadyExists(trainDTO.trainNumber());
        Train train = new Train(
                trainDTO.trainNumber(),
                trainDTO.trainType(),
                trainDTO.commissioningDate(),
                trainDTO.totalSeats()
        );

        trainRepository.save(train);
    }

    public void checkIfTrainAlreadyExists(Integer trainNumber) {
        boolean trainExists = trainRepository.existsByTrainNumber(trainNumber);
        if(trainExists) {
            throw new DuplicatedResourceException("Found duplicated train with ref %s".formatted(trainNumber));
        }
    }

    public List<TrainDTO> getTrains() {
        return trainRepository.findAll()
                .stream()
                .map(trainDTOMapper)
                .collect(Collectors.toList());
    }

    public TrainDTO getTrainByTrainNumber(Integer trainNumber) {
        return trainRepository.findTrainByTrainNumber(trainNumber)
                .map(trainDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No train was found with ref %s".formatted(trainNumber)
                ));
    }

    public Train getTrainByTrainNbr(Integer trainNumber) {
        return trainRepository.findTrainByTrainNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No train was found with ref %s".formatted(trainNumber)
                ));
    }
}
