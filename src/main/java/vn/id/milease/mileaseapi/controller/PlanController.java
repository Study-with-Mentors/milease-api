package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlanDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlanDto;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlanDto;
import vn.id.milease.mileaseapi.service.PlanService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    @GetMapping("/{id}")
    public PlanDto getPlan(@PathVariable long id) {
        return planService.getPlanById(id);
    }

    @GetMapping
    public PageResult<PlanDto> getPlans(PlanSearchDto searchDto) {
        return planService.getPlans(searchDto);
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
}
