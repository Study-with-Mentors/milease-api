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
import vn.id.milease.mileaseapi.model.exception.NotFoundException;
import vn.id.milease.mileaseapi.repository.PlanRepository;
import vn.id.milease.mileaseapi.service.PlanService;
import vn.id.milease.mileaseapi.service.util.ServiceUtil;
import vn.id.milease.mileaseapi.util.ApplicationMapper;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final ApplicationMapper mapper;

    @Override
    public PageResult<PlanDto> getPlans(PlanSearchDto searchDto) {
        var predicate = planRepository.prepareSearchPredicate(searchDto);
        PageRequest pageRequest = ServiceUtil.preparePageRequest(searchDto);
        Page<Plan> plans = planRepository.findAll(predicate, pageRequest);
        return ServiceUtil.toPageResult(plans, mapper.getPlanMapper()::toDto, searchDto);
    }

    @Override
    public PlanDto addPlan(CreatePlanDto dto) {
        Plan entity = mapper.getPlanMapper().toEntity(dto);
        // TODO [Duy, P2] validate that the start must be after today and end date must be after start date
        entity.setId(0L);
        entity = planRepository.save(entity);
        return mapper.getPlanMapper().toDto(entity);
    }

    @Override
    public PlanDto updatePlan(UpdatePlanDto dto) {
        Plan entity = getPlan(dto.getId());
        // TODO [Duy, P2] validate status
        mapper.getPlanMapper().toEntity(dto, entity);
        entity = planRepository.save(entity);
        return mapper.getPlanMapper().toDto(entity);
    }

    @Override
    public void deletePlan(long id) {
        var entity = getPlan(id);
        // TODO [Duy, P1] improve this
        planRepository.delete(entity);
    }

    private Plan getPlan(long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Plan.class, id));
    }
}
