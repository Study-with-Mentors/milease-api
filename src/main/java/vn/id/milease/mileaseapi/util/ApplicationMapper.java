package vn.id.milease.mileaseapi.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.DistrictDto;
import vn.id.milease.mileaseapi.model.dto.ProvinceDto;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.District;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.Province;

@Component
public class ApplicationMapper {
    private final ModelMapper mapper;

    @Autowired
    public ApplicationMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProvinceDto provinceToDto(Province province) {
        return mapper.map(province, ProvinceDto.class);
    }

    public ProvinceDto provinceToDto(Province province, boolean withDistricts) {
        return (withDistricts) ?
                mapper.typeMap(Province.class, ProvinceDto.class)
                        .addMapping(prov -> prov.getDistricts().stream().map(this::districtToDto).toList(), ProvinceDto::setDistricts)
                        .map(province) :
                provinceToDto(province);
    }

    public DistrictDto districtToDto(District district) {
        return mapper.map(district, DistrictDto.class);
    }
}
