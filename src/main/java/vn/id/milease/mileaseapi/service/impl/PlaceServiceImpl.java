package vn.id.milease.mileaseapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import vn.id.milease.mileaseapi.service.PlaceService;
import vn.id.milease.mileaseapi.util.mapper.PlaceMapper;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;
    @Override
    public PageResult<PlaceDto> getPlaces(PlaceSearchDto searchDto) {
        var predicate = placeRepository.prepareSearchPredicate(searchDto);
        PageRequest pageRequest = PageRequest.of(
                searchDto.getPage(),
                searchDto.getPageSize(),
                Sort.by(searchDto.getDirection(), searchDto.getOrderBy()));
        Page<Place> places = placeRepository.findAll(predicate, pageRequest);
        PageResult<PlaceDto> result = new PageResult<>();
        result.setValues(places.get()
                .map(placeMapper::toDto)
                .toList());
        result.setTotalPages(places.getTotalPages());
        result.setTotalCount(places.getTotalElements());
        result.setCurrentPage(searchDto.getPage());
        return result;
    }

    //TODO [Dat, P1]: Validating address and business
    @Override
    public PlaceDto addPlace(CreatePlaceDto dto) {
        var entityToAdd = placeMapper.toEntity(dto);
        entityToAdd.setDisplayIndex(calculateDisplayIndex());
        entityToAdd = placeRepository.save(entityToAdd);
        return placeMapper.toDto(entityToAdd);
    }

    //TODO [Dat, P1]: Validating address and business
    @Override
    public PlaceDto updatePlace(UpdatePlaceDto dto) {
        var entityToUpdate = placeRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(Place.class, dto.getId()));
        if(entityToUpdate.getStatus() == PlaceStatus.REMOVE)
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
        if(entityToDelete.getStatus() == PlaceStatus.REMOVE)
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
    private int calculateDisplayIndex() {
        var listFind = placeRepository.findAll()
                .stream()
                .map(Place::getDisplayIndex)
                .collect(Collectors.toSet());
        int numberOfConflict = 10;
        int countConflict = 0;
        while (countConflict < numberOfConflict) {
            Random random = new Random(Thread.currentThread().getId());
            int index = random.nextInt(Integer.MAX_VALUE);
            if(!listFind.contains(index))
                return index;
            countConflict++;
        }
        throw new ConflictException(Place.class, ActionConflict.CREATE, "This is our fault, cannot create displayIndex");
    }
}
