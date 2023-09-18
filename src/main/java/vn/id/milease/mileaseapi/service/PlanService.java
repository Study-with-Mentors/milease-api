package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlanDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlanDto;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlanDto;

public interface PlanService {
    PageResult<PlanDto> getPlans(PlanSearchDto searchDto);

    PlanDto addPlan(CreatePlanDto dto);

    PlanDto updatePlan(UpdatePlanDto dto);

    void deletePlan(long id);
}
