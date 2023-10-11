package vn.id.milease.mileaseapi.controller;

import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlanDto;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlanDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepFromPlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTailStepDto;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlanDto;
import vn.id.milease.mileaseapi.service.PlanService;
import vn.id.milease.mileaseapi.service.StepService;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;
    private final StepService stepService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public PlanDto getPlan(@PathVariable long id) {
        return planService.getPlanById(id);
    }

    @GetMapping
    public PageResult<PlanDto> getPlans(PlanSearchDto searchDto) {
        return planService.getPlans(searchDto);
    }

    @GetMapping("/count")
    public long countPlan(PlanSearchDto searchDto) {
        if (searchDto.getLowerDate() == null) {
            searchDto.setLowerDate(LocalDateTime.of(1990, 1, 1, 1, 1, 1));
        }
        if (searchDto.getUpperDate() == null) {
            searchDto.setUpperDate(LocalDateTime.of(3000, 1, 1, 1, 1, 1));
        }

        return planService.countPlan(searchDto);
    }

    @GetMapping("/{id}/steps")
    public List<StepDto> getPlanSteps(@PathVariable long id) {
        return stepService.getStepByPlanId(id);
    }

    @PostMapping("/{id}/steps")
    public StepDto addStepToPlan(@PathVariable long id, @RequestBody @Valid CreateStepDto dto) {
        dto.setPlanId(id);
        dto.setPlaceId(0);
        return stepService.addStep(dto);
    }

    @PostMapping("/{id}/steps/places/{placeId}")
    public StepDto addStepToPlanFromPlace(@PathVariable long id, @PathVariable long placeId, @RequestBody @Valid CreateStepFromPlaceDto createDto) {
        CreateStepDto dto = mapper.map(createDto, CreateStepDto.class);
        dto.setPlanId(id);
        dto.setPlaceId(placeId);
        return stepService.addStep(dto);
    }

    @PostMapping("/{id}/steps/last")
    public StepDto addStepToPlanTail(@PathVariable long id, @RequestBody @Valid CreateTailStepDto dto) {
        dto.setPlanId(id);
        return stepService.addTailStep(dto);
    }

    @PostMapping
    public PlanDto addPlan(@RequestBody CreatePlanDto dto) {
        return planService.addPlan(dto);
    }

    @PutMapping("/{id}")
    public PlanDto updatePlan(@PathVariable long id, @RequestBody UpdatePlanDto dto) {
        dto.setId(id);
        return planService.updatePlan(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable long id) {
        planService.deletePlan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/optimize")
    public List<StepDto> optimizeSteps(@PathVariable long id) throws IOException, InterruptedException, ApiException {
        return stepService.optimizePlan(id);
    }
}
