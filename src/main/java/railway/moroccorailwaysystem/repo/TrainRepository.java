package railway.moroccorailwaysystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import railway.moroccorailwaysystem.model.Train;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {
    Optional<Train> findTrainByTrainNumber(Integer trainNumber);
    boolean existsByTrainNumber(Integer trainNumber);
}
