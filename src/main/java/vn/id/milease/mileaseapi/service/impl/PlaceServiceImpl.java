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
import vn.id.milease.mileaseapi.util.PlaceMapper;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    @Override
    public PageResult<PlaceDto> getPlaces(PlaceSearchDto searchDto) {
        var predicate = placeRepository.prepareSearchPredicate(searchDto);
        PageRequest pageRequest = PageRequest.of(
                searchDto.getPage(),
                searchDto.getPageSize(),
                Sort.by(searchDto.getDirection(), searchDto.getOrderBy().getNameOfProperty()));
        Page<Place> places = placeRepository.findAll(predicate, pageRequest);
        PageResult<PlaceDto> result = new PageResult<>();
        result.setValues(places.get()
                .map(PlaceMapper::ToDto)
                .toList());
        result.setTotalPages(places.getTotalPages());
        result.setTotalCount(places.getTotalElements());
        return result;
    }

    //TODO: Validating address and business
    @Override
    public PlaceDto addPlace(CreatePlaceDto dto) {
        var entityToAdd = PlaceMapper.ToEntity(dto);
        entityToAdd.setDisplayIndex(calculateDisplayIndex());
        entityToAdd = placeRepository.save(entityToAdd);
        return PlaceMapper.ToDto(entityToAdd);
    }

    //TODO: validate address and business;
    @Override
    public PlaceDto updatePlace(UpdatePlaceDto dto) {
        var entityToUpdate = placeRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(Place.class, dto.getId()));
        if(entityToUpdate.getStatus() == PlaceStatus.REMOVE)
            throw new ConflictException(Place.class, ActionConflict.UPDATE, "Cannot update place that has been removed", null);
        PlaceMapper.ToEntity(dto, entityToUpdate);
        entityToUpdate = placeRepository.save(entityToUpdate);
        return PlaceMapper.ToDto(entityToUpdate);
    }
    private int calculateDisplayIndex() {
        var listFind = placeRepository.findAll()
                .stream()
                .map(Place::getDisplayIndex)
                .collect(Collectors.toSet());
        int numberOfConflict = 10;
        int countConflict = 0;
        while (countConflict < numberOfConflict) {
            Random random = new Random();
            int index = random.nextInt(Integer.MAX_VALUE);
            if(!listFind.contains(index))
                return index;
            countConflict++;
        }
        throw new ConflictException(Place.class, ActionConflict.CREATE, "This is our fault, cannot create displayIndex", null);
    }
}
