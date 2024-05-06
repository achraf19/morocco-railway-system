package railway.moroccorailwaysystem.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import railway.moroccorailwaysystem.dto.RailwayLineDTO;
import railway.moroccorailwaysystem.model.RailwayLine;
import railway.moroccorailwaysystem.service.RailwayLineService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/railways")
public class RailwayLineController {

    private final RailwayLineService railwayLineService;

    public RailwayLineController(RailwayLineService railwayLineService) {
        this.railwayLineService = railwayLineService;
    }

    @PostMapping("/save")
    public void saveNewRailwayLine(@RequestBody @Valid RailwayLineDTO railwayLineDTO) {
        railwayLineService.saveNewRailwayLine(railwayLineDTO);
    }

    @GetMapping
    public List<RailwayLine> getRailwayLines() {
        return railwayLineService.getRailwayLines();
    }

    @GetMapping("/{ref}")
    public RailwayLine getRailwayLineByRef(@RequestParam Integer ref) {
        return railwayLineService.getRailwayLineByRef(ref);
    }
}
