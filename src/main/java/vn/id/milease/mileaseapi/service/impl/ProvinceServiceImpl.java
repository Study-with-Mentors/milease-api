package vn.id.milease.mileaseapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.dto.ProvinceDto;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.Province;
import vn.id.milease.mileaseapi.repository.ProvinceRepository;
import vn.id.milease.mileaseapi.service.ProvinceService;
import vn.id.milease.mileaseapi.util.ApplicationMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {
    private final ApplicationMapper mapper;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceServiceImpl(ApplicationMapper mapper, ProvinceRepository provinceRepository) {
        this.mapper = mapper;
        this.provinceRepository = provinceRepository;
    }

    public List<ProvinceDto> getAll(boolean withDistricts) {
        List<Province> provinces = (withDistricts) ?
                provinceRepository.findAllWithDistricts() :
                provinceRepository.findAll();
        return provinces
                .stream()
                .map(p -> mapper.provinceToDto(p, withDistricts))
                .toList();
    }
}
