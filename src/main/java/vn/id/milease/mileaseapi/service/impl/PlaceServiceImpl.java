package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.PlaceDto;
import vn.id.milease.mileaseapi.model.dto.create.CreatePlaceDto;
import vn.id.milease.mileaseapi.model.dto.search.PlaceSearchDto;
import vn.id.milease.mileaseapi.model.dto.update.UpdatePlaceDto;
import vn.id.milease.mileaseapi.model.entity.place.Place;
import vn.id.milease.mileaseapi.model.entity.place.PlaceStatus;
import vn.id.milease.mileaseapi.model.exception.ActionConflict;
import vn.id.milease.mileaseapi.model.exception.ConflictException;
import vn.id.milease.mileaseapi.model.exception.NotFoundException;
import vn.id.milease.mileaseapi.repository.PlaceRepository;
import vn.id.milease.mileaseapi.repository.TransactionRepository;
import vn.id.milease.mileaseapi.service.PlaceService;
import vn.id.milease.mileaseapi.service.util.ServiceUtil;
import vn.id.milease.mileaseapi.util.mapper.PlaceMapper;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final TransactionRepository transactionRepository;
    private final PlaceMapper placeMapper;
    private final ZoneId VN_ZONE_ID = ZoneId.of("Asia/Ho_Chi_Minh");
    private final Random random = new Random(Thread.currentThread().getId());

    @Async
    @Override
    public CompletableFuture<PageResult<PlaceDto>> getPlacesAsync(PlaceSearchDto searchDto) {
        return CompletableFuture.completedFuture(this.getPlaces(searchDto));
    }

    @Override
    public PageResult<PlaceDto> getPlaces(PlaceSearchDto searchDto) {
        var predicate = placeRepository.prepareSearchPredicate(searchDto);

        PageRequest pageRequest = ServiceUtil.preparePageRequest(searchDto);
        Page<Place> places = placeRepository.findAll(predicate, pageRequest);
        return ServiceUtil.toPageResult(places, placeMapper::toDto, searchDto);
    }

    //TODO [Dat, P1]: Validating address and business
    @Override
    public PlaceDto addPlace(CreatePlaceDto dto) {
        // TODO [Dat, P3] validate open, close time
        // TODO [Dat, P3] validate lower, upper price
        var entityToAdd = placeMapper.toEntity(dto);
        entityToAdd.setDisplayIndex(calculateDisplayIndex());
        entityToAdd.setCreatedAt(LocalDateTime.now(VN_ZONE_ID));
        entityToAdd = placeRepository.save(entityToAdd);
        return placeMapper.toDto(entityToAdd);
    }

    //TODO [Dat, P1]: Validating address and business
    @Override
    public PlaceDto updatePlace(UpdatePlaceDto dto) {
        var entityToUpdate = placeRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(Place.class, dto.getId()));
        if (entityToUpdate.getStatus() == PlaceStatus.REMOVE)
            throw new ConflictException(Place.class, ActionConflict.UPDATE, "Cannot update place that has been removed");
        placeMapper.toEntity(dto, entityToUpdate);
        entityToUpdate = placeRepository.save(entityToUpdate);
        return placeMapper.toDto(entityToUpdate);
    }

    //TODO [Dat, P4]: real deleting when place is not related to anything
    @Override
    public void deletePlace(long id) {
        var entityToDelete = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Place.class, id));
        if (entityToDelete.getStatus() == PlaceStatus.REMOVE)
            throw new NotFoundException(Place.class, id);
        entityToDelete.setStatus(PlaceStatus.REMOVE);
        placeRepository.save(entityToDelete);
    }

    @Override
    public PlaceDto getPlacesById(long id) {
        var result = placeRepository.getPlaceById(id)
                .orElseThrow(() -> new NotFoundException(Place.class, id));
        return placeMapper.toDto(result);
    }

    //TODO [Dat, P2]: Calculate index base on business rule
    private long calculateDisplayIndex() {
        var listFind = placeRepository.findAll()
                .stream()
                .map(Place::getDisplayIndex)
                .collect(Collectors.toSet());
        int numberOfConflict = 10;
        int countConflict = 0;
        while (countConflict < numberOfConflict) {
            long index = random.nextLong(Long.MAX_VALUE);
            if (!listFind.contains(index))
                return index;
            countConflict++;
        }
        throw new ConflictException(Place.class, ActionConflict.CREATE, "This is our fault, cannot create displayIndex");
    }

    public Place getPlace(long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Place.class, id));
    }

    @Async
    @Override
    public CompletableFuture<Void> updateDisplayIndex() {
        //If the time interval from 'createdAt' to current of all transactions are less than or is equal to this variable
        //Bonus will be calculated
        long allowTimeIntervalToCalBonus = (long) 30 * 24 * 60 * 60;
        LocalDateTime currentTime = LocalDateTime.now(VN_ZONE_ID);
        var validPlaces = placeRepository.findAllByStatus(PlaceStatus.AVAILABLE);
        if (validPlaces.isEmpty())
            return CompletableFuture.completedFuture(null);
        for (var place : validPlaces) {
            long displayIndexAmountToAdd = ServiceUtil.calculateAmountOfDisplayIndex(place, currentTime, allowTimeIntervalToCalBonus);
            // Long.MAX_VALUE or Long.MIN_VALUE are symbolic for
            // allowHighestDisplayIndex or allowLowestDisplayIndex
            // which are defined by BR
            long amountToMinValue = place.getDisplayIndex() > 0 ? Long.MIN_VALUE + place.getDisplayIndex() : Long.MIN_VALUE - place.getDisplayIndex();
            long amountToMaxValue = place.getDisplayIndex() > 0 ? Long.MAX_VALUE - place.getDisplayIndex() : Long.MAX_VALUE + place.getDisplayIndex();
            if (displayIndexAmountToAdd < amountToMinValue) {
                place.setDisplayIndex(Long.MIN_VALUE);
                place.setStatus(PlaceStatus.UNAVAILABLE);
            } else if (amountToMaxValue < displayIndexAmountToAdd && place.getDisplayIndex() != Long.MAX_VALUE) {
                place.setDisplayIndex(Long.MAX_VALUE);
            } else
                place.setDisplayIndex(place.getDisplayIndex() + displayIndexAmountToAdd);
        }
        placeRepository.saveAll(validPlaces);
        return CompletableFuture.completedFuture(null);
    }
}
