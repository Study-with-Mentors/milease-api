package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlanDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlanDto;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlanDto;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;

public interface PlanService {
    PlanDto getPlanById(long id);

    PageResult<PlanDto> getPlans(PlanSearchDto searchDto);

    PlanDto addPlan(CreatePlanDto dto);

    PlanDto updatePlan(UpdatePlanDto dto);

    void deletePlan(long id);

    Plan getPlan(long id);

    PlanIdOnly getPlanIdOnly(long id);

    void checkCurrentUserPermission(Plan plan);

    void checkCurrentUserPermission(PlanIdOnly plan);

    void checkCurrentUserPermission(Long planId);
}
