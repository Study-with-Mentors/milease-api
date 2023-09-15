package vn.id.milease.mileaseapi.repository.custom;

import vn.id.milease.mileaseapi.model.entity.administrativeunit.Province;

import java.util.List;

public interface ProvinceRepositoryCustom {
    List<Province> findAllWithDistricts();
}
