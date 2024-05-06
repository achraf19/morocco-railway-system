package railway.moroccorailwaysystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import railway.moroccorailwaysystem.model.RailwayLine;

import java.util.Optional;

@Repository
public interface RailwayLineRepository extends JpaRepository<RailwayLine, Integer> {
    Optional<RailwayLine> findByRailwayLineNumber(Integer railwayLineNumber);
    boolean existsByRailwayLineNumber(Integer railwayLineNumber);

}
