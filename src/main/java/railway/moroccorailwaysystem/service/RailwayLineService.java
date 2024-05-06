package railway.moroccorailwaysystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.RailwayLineDTO;
import railway.moroccorailwaysystem.exception.ResourceNotFoundException;
import railway.moroccorailwaysystem.model.RailwayLine;
import railway.moroccorailwaysystem.repo.RailwayLineRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RailwayLineService {
    private final RailwayLineRepository railwayLineRepository;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RailwayLineService.class);

    public RailwayLineService(
            RailwayLineRepository railwayLineRepository
    ) {
        this.railwayLineRepository = railwayLineRepository;
    }

    public void saveNewRailwayLine(RailwayLineDTO railwayLineDTO) {
        checkIfRailwayLineExists(railwayLineDTO.railwayLineRef());
        RailwayLine railwayLine = new RailwayLine(
                railwayLineDTO.railwayLineRef(),
                railwayLineDTO.established()
        );

        railwayLineRepository.save(railwayLine);
    }

    public List<RailwayLine> getRailwayLines() {
        return new ArrayList<>(railwayLineRepository.findAll());
    }

    public RailwayLine getRailwayLineByRef(Integer railwayLineNumber) {
        return railwayLineRepository.findByRailwayLineNumber(railwayLineNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "no railway line was found with ref %s".formatted(railwayLineNumber)
                        )
                );
    }

    public void checkIfRailwayLineExists(Integer railwayLineNumber) {
        if(!railwayLineRepository.existsByRailwayLineNumber(railwayLineNumber)) {
            throw new ResourceNotFoundException(
                  "no railway line was found with ref %s".formatted(railwayLineNumber)
            );
        }
    }
}
