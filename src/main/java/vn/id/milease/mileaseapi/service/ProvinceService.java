package vn.id.milease.mileaseapi.service;

import vn.id.milease.mileaseapi.model.dto.ProvinceDto;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDto> getAll(boolean withDistricts);
}
