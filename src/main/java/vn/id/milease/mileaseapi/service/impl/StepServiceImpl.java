package vn.id.milease.mileaseapi.service.impl;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.StepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateStepDto;
import vn.id.milease.mileaseapi.model.dto.create.CreateTailStepDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdateStepDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.plan.Plan;
import vn.id.milease.mileaseapi.model.entity.plan.PlanIdOnly;
import vn.id.milease.mileaseapi.model.entity.step.Step;
import vn.id.milease.mileaseapi.model.entity.step.StepIdOnly;
import vn.id.milease.mileaseapi.model.exception.ArgumentsException;
import vn.id.milease.mileaseapi.model.exception.BadRequestException;
import vn.id.milease.mileaseapi.model.exception.ConflictException;
import vn.id.milease.mileaseapi.model.exception.NotFoundException;
import vn.id.milease.mileaseapi.repository.PlaceRepository;
import vn.id.milease.mileaseapi.repository.PlanRepository;
import vn.id.milease.mileaseapi.repository.StepRepository;
import vn.id.milease.mileaseapi.service.PlanService;
import vn.id.milease.mileaseapi.service.StepService;
import vn.id.milease.mileaseapi.util.ApplicationMapper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class StepServiceImpl implements StepService {
    private final StepRepository stepRepository;
    private final PlanRepository planRepository;
    private final PlanService planService;
    private final PlaceRepository placeRepository;
    private final ApplicationMapper mapper;

    @Value("${app.google.maps.api-key}")
    private String googleMapsApiKey;

    private void linkStep(StepIdOnly step, Long prevStepId, Long nextStepId) {
        linkPreviousStep(step, prevStepId);
        linkNextStep(step, nextStepId);
    }

    private void linkPreviousStep(StepIdOnly step, Long previousStepId) {
        step.setPreviousStepId(previousStepId);
        if (previousStepId != null) {
            stepRepository.updateNextStepById(previousStepId, step.getId());
        }
    }

    private void linkNextStep(StepIdOnly step, Long nextStepId) {
        step.setNextStepId(nextStepId);
        if (nextStepId != null) {
            stepRepository.updatePreviousStepById(nextStepId, step.getId());
        }
    }

    @Override
    public StepDto getStepById(long id) {
        return mapper.getStepMapper().toDto(getStep(id));
    }

    @Override
    public List<StepDto> getStepByPlanId(long planId) {
        Plan plan = planService.getPlan(planId);
        planService.checkCurrentUserPermission(plan);
        List<Step> orderSteps = orderSteps(plan.getFirstStep());
        return orderSteps.stream()
                .map(mapper.getStepMapper()::toDto)
                .toList();
    }

    @Override
    public void moveStep(long stepId, long toStepId) {
        if (stepId == toStepId) {
            throw new ConflictException("Cannot move the same step");
        }
        StepIdOnly step = getStepIdOnly(stepId);
        StepIdOnly toStep = null;
        // if to step id == 0 => move to head
        if (toStepId != 0) {
            toStep = getStepIdOnly(toStepId);
            if (!step.getPlanId().equals(toStep.getPlanId())) {
                throw new ArgumentsException("Two steps do not belong to a plan");
            }
        }

        PlanIdOnly plan = planService.getPlanIdOnly(step.getPlanId());
        planService.checkCurrentUserPermission(plan);
        if (step.getPreviousStepId() == null) {
            // Update the head of linked list
            stepRepository.updatePreviousStepById(step.getNextStepId(), null);
            plan.setFirstStepId(step.getNextStepId());
            planRepository.updatePlan(plan);
        } else {
            // Update the next/prev link
            Long prevStepId = step.getPreviousStepId();
            Long nextStepId = step.getNextStepId();

            stepRepository.updateNextStepById(prevStepId, nextStepId);
            if (nextStepId != null) {
                stepRepository.updatePreviousStepById(nextStepId, prevStepId);
            }
        }
        if (toStep != null) {
            linkStep(step, toStep.getId(), toStep.getNextStepId());
        } else {
            linkStep(step, null, plan.getFirstStepId());
            plan.setFirstStepId(step.getId());
            planRepository.updatePlan(plan);
        }
        stepRepository.updateStepPosition(step);
    }

    @Override
    public void swapStep(long step1Id, long step2Id) {
        if (step1Id == step2Id) {
            throw new ConflictException("Cannot swap the same step");
        }
        StepIdOnly step1 = getStepIdOnly(step1Id);
        StepIdOnly step2 = getStepIdOnly(step2Id);
        if (!step1.getPlanId().equals(step2.getPlanId())) {
            throw new ArgumentsException("Two steps do not belong to a plan");
        }
        planService.checkCurrentUserPermission(step1.getPlanId());

        if (step1.getNextStepId().equals(step2.getId()) || step1.getPreviousStepId().equals(step2.getId())) {
            // adjacent node case: P <-> A(1) <-> B(2) <-> N
            // we want to swap A and B

            // swap the node for case P <-> B(2) <-> A(1) <-> N
            if (step1.getPreviousStepId().equals(step2.getId())) {
                StepIdOnly tmp = step1;
                step1 = step2;
                step2 = tmp;
            }

            // set link between A and N
            linkNextStep(step1, step2.getNextStepId());
            // set link between B and P
            linkPreviousStep(step2, step1.getPreviousStepId());

            // swap link between A and B
            step1.setPreviousStep(step2);
            step2.setNextStep(step1);

        } else {
            // case there is node in the middle P <-> A <-> C <-> B <-> N
            // map A to C and N, vice versa
            StepIdOnly temp = new StepIdOnly(step1.getId(), step1.getPreviousStepId(), step1.getNextStepId(), step1.getPlanId());
            linkStep(step1, step2.getPreviousStepId(), step2.getNextStepId());
            // map B to C and P, vice versa
            linkStep(step2, temp.getPreviousStepId(), temp.getNextStepId());
        }

        stepRepository.updateStepPosition(step1);
        stepRepository.updateStepPosition(step2);
    }

    //TODO [Dat, P4]: More validation for geo
    private void validateGeometric(CreateStepDto dto) {
        if (!((dto.getLongitude() != null && dto.getLatitude() != null) || dto.getPlaceId() != 0)) {
            throw new BadRequestException("Longitude and Latitude of %s both must be null or have value");
        }
    }

    @Override
    public StepDto addStep(CreateStepDto dto) {
        validateGeometric(dto);

        Place place = null;
        if (dto.getPlaceId() != 0) {
            place = placeRepository.findById(dto.getPlaceId()).orElse(null);
        }
        PlanIdOnly plan = planService.getPlanIdOnly(dto.getPlanId());
        planService.checkCurrentUserPermission(plan);

        Step stepEntity = mapper.getStepMapper().toEntity(dto);
        if (place != null) {
            stepEntity.setLongitude(place.getLongitude());
            stepEntity.setLatitude(place.getLatitude());
            stepEntity.setAddressString(place.getAddressString());
            stepEntity.setPlaceName(place.getName());
            stepEntity.setPlace(place);
        }
        stepEntity.setId(0L);
        stepEntity = stepRepository.save(stepEntity);
        StepIdOnly step = getStepIdOnly(stepEntity.getId());
        step.setPlan(plan);

        if (dto.getPreviousStepId() == null) {
            // insert to the head of plan
            if (plan.getFirstStepId() != null) {
                step.setNextStepId(plan.getFirstStepId());
                stepRepository.updatePreviousStepById(plan.getFirstStepId(), step.getId());
            }
            plan.setFirstStep(step);
        } else {
            if (plan.getFirstStepId() == null) {
                // insert the first step in plan
                plan.setFirstStep(step);
            } else {
                // insert step in the middle of linked list
                StepIdOnly prevStep = getStepIdOnly(dto.getPreviousStepId());

                step.setPreviousStep(prevStep);
                step.setNextStepId(prevStep.getNextStepId());
                if (prevStep.getNextStepId() != null) {
                    stepRepository.updatePreviousStepById(prevStep.getNextStepId(), step.getId());
                }
                prevStep.setNextStep(step);
                stepRepository.updateStepPosition(prevStep);
            }
        }

        planRepository.updatePlan(plan);
        stepRepository.updateStepPosition(step);
        StepDto resultDto = mapper.getStepMapper().toDto(stepEntity);
        resultDto.setNextStepId(step.getNextStepId());
        resultDto.setPreviousStepId(step.getPreviousStepId());
        return resultDto;
    }

    @Override
    public StepDto addTailStep(CreateTailStepDto dto) {
        CreateStepDto createDto = mapper.getStepMapper().toCreateDto(dto);
        StepIdOnly lastStep = getLastStepOfPlan(dto.getPlanId());
        if (lastStep != null) {
            createDto.setPreviousStepId(lastStep.getId());
        }
        return addStep(createDto);
    }

    @Override
    public StepDto updateStep(UpdateStepDto dto) {
        Step step = getStep(dto.getId());
        planService.checkCurrentUserPermission(step.getPlan());
        mapper.getStepMapper().toEntity(dto, step);

        step = stepRepository.save(step);
        return mapper.getStepMapper().toDto(step);
    }

    @Override
    public void deleteStep(long id) {
        StepIdOnly step = getStepIdOnly(id);
        PlanIdOnly plan = planService.getPlanIdOnly(step.getPlanId());
        planService.checkCurrentUserPermission(plan);
        if (step.getPreviousStepId() == null) {
            // Update the head of linked list
            if (step.getNextStepId() != null) {
                stepRepository.updatePreviousStepById(step.getNextStepId(), null);
                plan.setFirstStepId(step.getNextStepId());
            } else {
                plan.setFirstStep(null);
            }
            planRepository.updatePlan(plan);
        } else {
            // Update the next/prev link
            Long prevStepId = step.getPreviousStepId();
            Long nextStepId = step.getNextStepId();

            stepRepository.updateNextStepById(prevStepId, nextStepId);
            if (nextStepId != null) {
                stepRepository.updatePreviousStepById(nextStepId, prevStepId);
            }
        }
        stepRepository.deleteById(id);
    }

    private Step getStep(long id) {
        return stepRepository.findById(id).orElseThrow(() -> new NotFoundException(Step.class, id));
    }

    private StepIdOnly getStepIdOnly(long id) {
        return stepRepository.findIdOnlyById(id).orElseThrow(() -> new NotFoundException(Step.class, id));
    }

    @Override
    public List<StepDto> optimizePlan(long planId) throws IOException, InterruptedException, ApiException {
        Plan plan = planService.getPlan(planId);
        planService.checkCurrentUserPermission(plan);

        List<Step> steps = orderSteps(plan.getFirstStep());
        if (steps.size() < 4) {
            throw new BadRequestException("Plan must have at least 4 steps");
        }

        LinkedList<LatLng> waypoints = steps.stream()
                .map(step -> new LatLng(step.getLatitude(), step.getLongitude()))
                .collect(Collectors.toCollection(LinkedList::new));
        LatLng origin = waypoints.pop();
        LatLng destination = waypoints.removeLast();

        DirectionsRoute[] routes = DirectionsApi.newRequest(new GeoApiContext.Builder().apiKey(googleMapsApiKey).build())
                .mode(TravelMode.DRIVING)
                .origin(origin)
                .waypoints(waypoints.toArray(new LatLng[0]))
                .destination(destination)
                .departureTime(plan.getStart().toInstant(ZoneOffset.ofHours(7)))
                .optimizeWaypoints(true)
                .await().routes;

        if (routes.length == 0) {
            throw new BadRequestException("Cannot optimize steps");
        }

        DirectionsLeg[] legs = routes[0].legs;
        int[] waypointOrder = routes[0].waypointOrder;
        List<Step> optimizedSteps = new ArrayList<>(steps.size());

        // Update first step
        Step firstStep = steps.get(0);
        firstStep.setLatitude((float) legs[0].startLocation.lat);
        firstStep.setLongitude((float) legs[0].startLocation.lng);
        firstStep.setNextStep(steps.get(waypointOrder[0] + 1));
        steps.get(waypointOrder[0] + 1).setPreviousStep(firstStep);
        optimizedSteps.add(firstStep);

        // Update waypoints
        for (int i = 0; i < waypointOrder.length; i++) {
            Step step = steps.get(waypointOrder[i] + 1);
            int nextIndex = (i < waypointOrder.length - 1) ? waypointOrder[i + 1] + 1 : steps.size() - 1;
            step.setLatitude((float) legs[i + 1].startLocation.lat);
            step.setLongitude((float) legs[i + 1].startLocation.lng);
            step.setDuration((float) legs[i].duration.inSeconds);
            step.setDistance((float) legs[i].distance.inMeters);
            step.setNextStep(steps.get(nextIndex));
            steps.get(nextIndex).setPreviousStep(step);
            optimizedSteps.add(step);
        }

        // Update last step
        Step lastStep = steps.get(steps.size() - 1);
        lastStep.setLatitude((float) legs[legs.length - 1].endLocation.lat);
        lastStep.setLongitude((float) legs[legs.length - 1].endLocation.lng);
        lastStep.setDuration((float) legs[legs.length - 1].duration.inSeconds);
        lastStep.setDistance((float) legs[legs.length - 1].distance.inMeters);
        optimizedSteps.add(lastStep);

        stepRepository.saveAll(optimizedSteps);
        return optimizedSteps.stream()
                .map(mapper.getStepMapper()::toDto)
                .toList();
    }

    private List<Step> orderSteps(Step firstStep) {
        List<Step> steps = new ArrayList<>();
        Step currentStep = firstStep;
        while (currentStep != null) {
            steps.add(currentStep);
            currentStep = currentStep.getNextStep();
        }
        return steps;
    }

    public StepIdOnly getLastStepOfPlan(long planId) {
        return stepRepository.findStepByPlanIdAndNextStepIdIsNull(planId).orElse(null);
    }
}
