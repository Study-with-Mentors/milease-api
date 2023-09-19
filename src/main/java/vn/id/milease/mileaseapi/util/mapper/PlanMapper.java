package vn.id.milease.mileaseapi.util.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.PlanDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlanDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlanDto;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;

@RequiredArgsConstructor
@Component
public class PlanMapper implements Mapper<Plan, PlanDto, CreatePlanDto, UpdatePlanDto> {
    private final ModelMapper mapper;
    @Override
    public Plan toEntity(CreatePlanDto planDto) {
        return mapper.map(planDto, Plan.class);
    }

    @Override
    public void toEntity(UpdatePlanDto planDto, Plan plan) {
        mapper.map(planDto, plan);
    }

    @Override
    public PlanDto toDto(Plan plan) {
        return mapper.map(plan, PlanDto.class);
    }
}
