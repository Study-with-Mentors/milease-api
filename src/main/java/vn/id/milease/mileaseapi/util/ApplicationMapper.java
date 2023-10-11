package vn.id.milease.mileaseapi.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.dto.DistrictDto;
import vn.id.milease.mileaseapi.model.dto.ProvinceDto;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.District;
import vn.id.milease.mileaseapi.model.entity.administrativeunit.Province;
import vn.id.milease.mileaseapi.util.mapper.PlaceMapper;
import vn.id.milease.mileaseapi.util.mapper.PlanMapper;
import vn.id.milease.mileaseapi.util.mapper.StepMapper;
import vn.id.milease.mileaseapi.util.mapper.UserMapper;

@Component
@RequiredArgsConstructor
@Getter
public class ApplicationMapper {
    private final ModelMapper mapper;
    private final PlanMapper planMapper;
    private final PlaceMapper placeMapper;
    private final StepMapper stepMapper;
    private final UserMapper userMapper;

    public ProvinceDto provinceToDto(Province province) {
        return mapper.typeMap(Province.class, ProvinceDto.class)
                .addMappings(map -> map.skip(ProvinceDto::setDistricts))
                .map(province);
    }

    public ProvinceDto provinceToDto(Province province, boolean withDistricts) {
        ProvinceDto dto = provinceToDto(province);
        if (withDistricts) {
            dto.setDistricts(
                    province.getDistricts().stream()
                            .map(this::districtToDto)
                            .toList()
            );
        }
        return dto;
    }

    public DistrictDto districtToDto(District district) {
        return mapper.map(district, DistrictDto.class);
    }
}
