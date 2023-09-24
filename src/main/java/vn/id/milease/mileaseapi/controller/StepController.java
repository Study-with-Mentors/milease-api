package vn.id.milease.mileaseapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.service.StepService;

@RestController
@RequestMapping("/steps")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;

    @GetMapping("/{id}")
    public StepDto getStep(@PathVariable long id) {
        return stepService.getStepById(id);
    }

    @PutMapping("/{id}")
    public StepDto updateStep(@PathVariable long id, @RequestBody UpdateStepDto dto) {
        dto.setId(id);
        return stepService.updateStep(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable long id) {
        stepService.deleteStep(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
