package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlanDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlanDto;
import vn.id.milease.mileaseapi.model.dto.search.PlanSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlanDto;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.model.exception.ActionConflict;
import vn.id.milease.mileaseapi.model.exception.ArgumentsException;
import vn.id.milease.mileaseapi.model.exception.ForbiddenException;
import vn.id.milease.mileaseapi.model.exception.NotFoundException;
import vn.id.milease.mileaseapi.repository.PlanRepository;
import vn.id.milease.mileaseapi.service.PlanService;
import vn.id.milease.mileaseapi.service.UserService;
import vn.id.milease.mileaseapi.service.util.ServiceUtil;
import vn.id.milease.mileaseapi.util.ApplicationMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final UserService userService;
    private final ApplicationMapper mapper;

    @Override
    public PlanDto getPlanById(long id) {
        Plan plan = getPlan(id);
        checkCurrentUserPermission(plan);
        return mapper.getPlanMapper().toDto(plan);
    }

    @Override
    public PageResult<PlanDto> getPlans(PlanSearchDto searchDto) {
        var predicate = planRepository.prepareSearchPredicate(searchDto, userService.getCurrentUser().getId());
        PageRequest pageRequest = ServiceUtil.preparePageRequest(searchDto);
        Page<Plan> plans = planRepository.findAll(predicate, pageRequest);
        return ServiceUtil.toPageResult(plans, mapper.getPlanMapper()::toDto, searchDto);
    }

    @Override
    public PlanDto addPlan(CreatePlanDto dto) {
        checkDatetime(dto.getStart(), dto.getEnd());
        Plan entity = mapper.getPlanMapper().toEntity(dto);
        entity.setUser(userService.getCurrentUser());
        entity.setId(0L);
        entity = planRepository.save(entity);
        return mapper.getPlanMapper().toDto(entity);
    }

    @Override
    public PlanDto updatePlan(UpdatePlanDto dto) {
        checkDatetime(dto.getStart(), dto.getEnd());
        Plan entity = getPlan(dto.getId());
        checkCurrentUserPermission(entity);
        // TODO [Duy, P2] validate status
        mapper.getPlanMapper().toEntity(dto, entity);
        entity = planRepository.save(entity);
        return mapper.getPlanMapper().toDto(entity);
    }

    private void checkDatetime(LocalDateTime dto, LocalDateTime dto1) {
        // TODO [Duy, P3]: differentiate between create and update plan
        if (!dto.isAfter(LocalDateTime.now())) {
            throw new ArgumentsException(Plan.class, ActionConflict.CREATE, "Start time must be after now");
        }
        if (!dto.isBefore(dto1)) {
            throw new ArgumentsException(Plan.class, ActionConflict.CREATE, "Start time cannot be after end time");
        }
    }

    @Override
    public long countPlan(PlanSearchDto searchDto) {
        return planRepository.countByStartBetween(searchDto.getLowerDate(), searchDto.getUpperDate());
    }

    @Override
    public void deletePlan(long id) {
        Plan entity = getPlan(id);
        checkCurrentUserPermission(entity);
        // TODO [Duy, P3] improve this
        planRepository.delete(entity);
    }

    @Override
    public void checkCurrentUserPermission(Plan plan) {
        User user = userService.getCurrentUser();
        if (!plan.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(Plan.class, user.getId(), plan.getId());
        }
    }

    @Override
    public void checkCurrentUserPermission(PlanIdOnly plan) {
        User user = userService.getCurrentUser();
        if (!plan.getUserId().equals(user.getId())) {
            throw new ForbiddenException(Plan.class, user.getId(), plan.getId());
        }
    }

    @Override
    public void checkCurrentUserPermission(Long planId) {
        checkCurrentUserPermission(getPlanIdOnly(planId));
    }

    @Override
    public Plan getPlan(long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Plan.class, id));
    }

    @Override
    public PlanIdOnly getPlanIdOnly(long id) {
        return planRepository.findIdOnlyById(id)
                .orElseThrow(() -> new NotFoundException(Plan.class, id));
    }
}
